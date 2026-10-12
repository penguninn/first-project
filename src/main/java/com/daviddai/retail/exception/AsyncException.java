package com.daviddai.retail.exception;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.SwingWorker;

public final class AsyncException {

    public static <T> void run(Supplier<T> job, Consumer<T> onSuccess) {
        run(job, onSuccess, GlobalExceptionHandler::handle, () -> {
        });
    }

    public static <T> void run(
            Supplier<T> job,
            Consumer<T> onSuccess,
            Consumer<Throwable> onError,
            Runnable onFinally) {
        new SwingWorker<T, Void>() {

            @Override
            protected T doInBackground() throws Exception {
                return job.get();
            }

            @Override
            protected void done() {
                try {
                    onSuccess.accept(get());
                } catch (ExecutionException e) {
                    onError.accept(e.getCause());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    onFinally.run();
                }
            }

        }.execute();
    }
}
