package com.suprun.periodicals.view.command;

import com.suprun.periodicals.view.FrontController;
import com.suprun.periodicals.view.util.RedirectType;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class that carries information about Command executing result
 * so {@code FrontController} could know where and how redirect further
 *
 * @author Andrei Suprun
 * @see FrontController
 * @see Command
 */
public class CommandResult {

    private final RedirectType redirectType;
    private final String pagePath;

    private CommandResult(RedirectType redirectType, String pagePath) {
        this.redirectType = redirectType;
        this.pagePath = pagePath;
    }

    public static CommandResult of(RedirectType redirectType, String pagePath) {
        return new CommandResult(Objects.requireNonNull(redirectType), Objects.requireNonNull(pagePath));
    }

    public static CommandResult redirect(String pagePath) {
        return CommandResult.of(RedirectType.REDIRECT, Objects.requireNonNull(pagePath));
    }

    public static CommandResult forward(String pagePath) {
        return CommandResult.of(RedirectType.FORWARD, Objects.requireNonNull(pagePath));
    }

    public RedirectType getRedirectType() {
        return redirectType;
    }

    public String getPagePath() {
        return pagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandResult that = (CommandResult) o;
        return redirectType == that.redirectType && pagePath.equals(that.pagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(redirectType, pagePath);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CommandResult.class.getSimpleName() + "[", "]")
                .add("redirectType=" + redirectType)
                .add("pagePath='" + pagePath + "'")
                .toString();
    }
}
