package net.volodin.intercom;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Creates intercommunicate interface between server and client\
 *
 * @author Victor Volodin
 */

public class Intercom implements Closeable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    /**
     * Creates intercom instance for client-side
     *
     * @throws IOException If an I/O error occurs
     */
    public Intercom(String ipAddress, int port) {
        try {
            this.socket = new Socket(ipAddress, port);
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates server-side socket from ServerSocket instance
     *
     * @param server ServerSocket instance
     */
    public Intercom(ServerSocket server) {
        try {
            this.socket = server.accept();
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends message via Buffered writer
     *
     * @param message String message to be sent
     */
    public void writeLine(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads string data from Buffered reader
     *
     * @return Red string line
     */
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates buffer reader out of input stream
     *
     * @return buffered reader instance
     * @throws IOException If an I/O error occurs
     */
    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Creates buffer writer out of output stream
     *
     * @return buffered writer instance
     * @throws IOException If an I/O error occurs
     */
    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
