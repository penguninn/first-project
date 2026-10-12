package com.daviddai.retail.exception;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static void install() {
        Thread.setDefaultUncaughtExceptionHandler((thread, e) -> handle(e));
    }

    public static void handle(Throwable e) {
        Runnable task = () -> {
            if (e instanceof BusinessException be) {
                log.error("BusinessExceptio", e);
            } else {
                log.error("Unexpected error", e);
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            task.run();
        } else {
            SwingUtilities.invokeLater(task);
        }
    }
}
