package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        StringBuilder str = new StringBuilder();

        try(RandomAccessFile aFile = new RandomAccessFile(file, "r"); FileChannel inChannel = aFile.getChannel()){
            ByteBuffer buffer = ByteBuffer.allocate(48);


            while (inChannel.read(buffer) > 0){
                buffer.flip();

                for(int i =0; i < buffer.limit(); i++){
                    str.append((char) buffer.get());
                }

                buffer.clear();
            }

        }catch (IOException e){
            return new Profile();
        }

        return getProfile(str.toString());
    }

    private static Profile getProfile(String string) {
        String name ="";
        int age =0;
        String email = "";
        long phone = 0;

        String[] lines = string.split(System.lineSeparator());

        for (String s : lines) {
            String[] line = s.split(" ");

            if (line[0].contains("Name")) {
                name = line[1];
            } else if (line[0].contains("Age")) {
                age = Integer.parseInt(line[1]);
            } else if (line[0].contains("Email")) {
                email = line[1];
            } else if (line[0].contains("Phone")) {
                phone = Long.parseLong(line[1]);
            }
        }
        return (name.isEmpty())? new Profile(): new Profile(name,age,email,phone);
    }
}
