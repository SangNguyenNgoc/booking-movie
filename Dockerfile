# Sử dụng hình ảnh Maven chính thức từ DockerHub
FROM maven:3.8.4-openjdk-17 AS build

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy toàn bộ mã nguồn vào container
COPY . .

# Cài đặt các dependencies của Maven và chạy ứng dụng bằng lệnh spring-boot:run
CMD ["mvn", "spring-boot:run"]