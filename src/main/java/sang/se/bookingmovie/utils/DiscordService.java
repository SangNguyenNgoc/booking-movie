package sang.se.bookingmovie.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class DiscordService {

    @Value("${discord.token}")
    private String botToken;

    @Value("${discord.poster_channel}")
    private String posterChannel;

    @Value("${discord.image_channel}")
    private String imageChannel;

    @Value("${discord.avatar_channel}")
    private String avatarChannel;

    private JDA jda;

    @EventListener(ApplicationReadyEvent.class)
    private void loginDiscord() {
        try {
            jda = JDABuilder.createDefault(botToken).build();
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String sendImage(MultipartFile multipartFile, boolean cases) {
        CompletableFuture<String> cdnUrlFuture = new CompletableFuture<>();
        try {
            TextChannel textChannel;
            if(cases) {
                textChannel = jda.getTextChannelsByName(posterChannel, false).get(0);
            } else {
                textChannel = jda.getTextChannelsByName(imageChannel, false).get(0);
            }
            String name = Objects.requireNonNull(multipartFile.getOriginalFilename());
            byte[] fileData = multipartFile.getBytes();
            InputStream inputStream = new ByteArrayInputStream(fileData);
            if(textChannel != null) {
                textChannel.sendFiles(FileUpload.fromData(inputStream, name)).queue(message -> {
                    String cdnUrl = message.getAttachments().get(0).getUrl();
                    cdnUrlFuture.complete(cdnUrl);
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return cdnUrlFuture.join();
    }

    public String sendAvatar(MultipartFile multipartFile) {
        CompletableFuture<String> cdnUrlFuture = new CompletableFuture<>();
        try {
            TextChannel textChannel = jda.getTextChannelsByName(avatarChannel, false).get(0);;
            String name = Objects.requireNonNull(multipartFile.getOriginalFilename());
            byte[] fileData = multipartFile.getBytes();
            InputStream inputStream = new ByteArrayInputStream(fileData);
            if(textChannel != null) {
                textChannel.sendFiles(FileUpload.fromData(inputStream, name)).queue(message -> {
                    String cdnUrl = message.getAttachments().get(0).getUrl();
                    cdnUrlFuture.complete(cdnUrl);
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return cdnUrlFuture.join();
    }

    public void sendMessage(String message) {
        TextChannel textChannel;
        textChannel = jda.getTextChannelsByName("test", false).get(0);
        if(textChannel != null) {
            textChannel.sendMessage(message).queue();
        }

    }
}
