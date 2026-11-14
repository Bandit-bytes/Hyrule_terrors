package net.bandit.hyrule_terrors.util;

import java.util.function.BooleanSupplier;

/**
 * Server-safe access to the "is shift held" check used by tooltips. Item classes live in common code and are loaded on
 * dedicated servers, so they must not reference client-only classes such as {@code Screen} directly. The client
 * entrypoint installs the real supplier at startup.
 */
public final class TooltipHelper {

    private static BooleanSupplier shiftDown = () -> false;

    private TooltipHelper() {}

    public static void setShiftSupplier(BooleanSupplier supplier) {
        shiftDown = supplier;
    }

    public static boolean hasShiftDown() {
        return shiftDown.getAsBoolean();
    }
}
