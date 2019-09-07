package web.tool.sniffer;

@FunctionalInterface
public interface NativeMappingsFunction<T, R> {
    R apply(T t);
}
