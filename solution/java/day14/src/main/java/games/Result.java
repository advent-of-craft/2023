package games;

public class Result<T> {
    private T success;

    public Result(T success) {
        this.success = success;
    }

    public Result() {
    }

    public static <T> Result<T> fromSuccess(T success) {
        return new Result<>(success);
    }

    public static <T> Result<T> failure() {
        return new Result<>();
    }

    public T success() {
        return success;
    }

    public boolean failed() {
        return success == null;
    }
}