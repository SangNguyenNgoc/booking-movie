package sang.se.bookingmovie.vnpay;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.bill.BillEntity;
import sang.se.bookingmovie.exception.AllException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class VnpayService {

    private final VnpayConfig vnpayConfig;
    public String doPost(BillEntity billEntity) throws UnsupportedEncodingException {

        String orderType = "other";
        long amount = (long) (billEntity.getTotal() * 100);

        String vnp_TxnRef = billEntity.getTransactionId();
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = vnpayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnpayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", vnpayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("vnp_BankCode", bankCode);

//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnp_Params.put("vnp_BankCode", bankCode);
//        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + billEntity.getId());
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", orderType);

//        String locate = req.getParameter("language");
//        if (locate != null && !locate.isEmpty()) {
//            vnp_Params.put("vnp_Locale", locate);
//        } else {
//            vnp_Params.put("vnp_Locale", "vn");
//        }
        vnp_Params.put("vnp_ReturnUrl", vnpayConfig.vnp_ReturnUrl+"?id=" + billEntity.getTransactionId());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(billEntity.getCreateTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(billEntity.getCreateTime());
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnpayConfig.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }

    public Integer verifyPay(BillEntity billEntity) throws ServletException, IOException, JSONException {
        String vnp_RequestId = vnpayConfig.getRandomNumber(8);
        String vnp_Command = "querydr";
        String vnp_TxnRef = billEntity.getTransactionId();
        String vnp_OrderInfo = "Kiem tra ket qua GD don hang " + vnp_TxnRef;

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        String vnp_TransDate = formatter.format(billEntity.getCreateTime().getTime());

        String vnp_IpAddr = "127.0.0.1";

//        try (final DatagramSocket socket = new DatagramSocket()) {
//            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
//            vnp_IpAddr = socket.getLocalAddress().getHostAddress();
//        }

        JsonObject vnp_Params = new JsonObject();

        vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
        vnp_Params.addProperty("vnp_Version", vnpayConfig.vnp_Version);
        vnp_Params.addProperty("vnp_Command", vnp_Command);
        vnp_Params.addProperty("vnp_TmnCode", vnpayConfig.vnp_TmnCode);
        vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
        // vnp_Params.put("vnp_TransactionNo", vnp_TransactionNo);
        vnp_Params.addProperty("vnp_TransactionDate", vnp_TransDate);
        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

        String hash_Data = vnp_RequestId + "|" + vnpayConfig.vnp_Version + "|" + vnp_Command + "|" + vnpayConfig.vnp_TmnCode + "|" + vnp_TxnRef
                + "|" + vnp_TransDate + "|" + vnp_CreateDate + "|" + vnp_IpAddr + "|" + vnp_OrderInfo;

        String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.secretKey, hash_Data.toString());

        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

        URL url = new URL(vnpayConfig.vnp_ApiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(vnp_Params.toString());
        wr.flush();
        wr.close();

        System.out.println(url);
        System.out.println(vnp_Params);

        // int responseCode = con.getResponseCode();
        // System.out.println("nSending 'POST' request to URL : " + url);
        // System.out.println("Post Data : " + vnp_Params);
        // System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        System.out.println(response.toString());

        JSONObject json = new JSONObject(response.toString());
        System.out.println(json.toString());

        String res_ResponseCode = (String) json.get("vnp_ResponseCode");
//        String res_TxnRef = (String) json.get("vnp_TxnRef");
        String res_Message = (String) json.get("vnp_Message");
//        Double res_Amount = Double.valueOf((String) json.get("vnp_Amount")) / 100;
        String res_TransactionType = (String) json.get("vnp_TransactionType");
        String res_TransactionStatus = (String) json.get("vnp_TransactionStatus");

        System.out.println(res_Message);
        checkResponse(res_ResponseCode, res_TransactionType, res_TransactionStatus);

        return 0;
    }

    public void checkResponse(String res_ResponseCode, String res_TransactionType, String res_TransactionStatus){
        if (res_ResponseCode.equals("09")) // Response Code invaild
            throw new AllException("Transaction failed", 402, List.of("Thẻ/Tài khoản của khách hàng chưa đăng ký dịch vụ InternetBanking tại ngân hàng"));

        if (res_ResponseCode.equals("10")) // Response Code invaild
            throw new AllException("Transaction failed", 402, List.of("Khách hàng xác thực thông tin thẻ/tài khoản không đúng quá 3 lần"));

        if (res_ResponseCode.equals("11")) // Response Code invaild
            throw new AllException("Transaction failed", 402, List.of("Đã hết hạn chờ thanh toán. Xin quý khách vui lòng thực hiện lại giao dịch."));

        if (res_ResponseCode.equals("12")) // Response Code invaild
            throw new AllException("Transaction failed", 402, List.of("Thẻ/Tài khoản của khách hàng bị khóa."));

        if (res_ResponseCode.equals("24")) // Response Code invaild
            throw new AllException("Transaction failed", 402, List.of("Khách hàng hủy giao dịch."));

        if (res_ResponseCode.equals("51")) // Response Code invaild
            throw new AllException("Transaction failed", 402, List.of("Tài khoản của quý khách không đủ số dư để thực hiện giao dịch."));

        if (res_ResponseCode.equals("65")) // Response Code invaild
            throw new AllException("Transaction failed", 402, List.of("Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày."));

        if (res_ResponseCode.equals("75")) // Response Code invaild
            throw new AllException("Transaction failed", 402, List.of("Ngân hàng thanh toán đang bảo trì.."));

        if (res_ResponseCode.equals("79")) // Response Code invaild
            throw new AllException("Transaction failed", 402, List.of("KH nhập sai mật khẩu thanh toán quá số lần quy định. Xin quý khách vui lòng thực hiện lại giao dịch"));

        if (res_ResponseCode.equals("99")) // Response Code invaild
            throw new AllException("Transaction failed", 402, List.of("Lỗi không xác định."));

        if (!res_TransactionType.equals("01")) // Transaction Type invaild
            throw new AllException("Transaction failed", 402, List.of("Transaction Type invaild"));

        if (res_TransactionStatus.equals("01")) // Transaction is pending
            throw new AllException("Transaction failed", 402, List.of("Chưa thanh toán"));

        if (!res_TransactionStatus.equals("00")) // Transaction Status invaild
            throw new AllException("Transaction failed", 402, List.of("Transaction Status invaild"));
    }

}
