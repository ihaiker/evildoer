package la.renzhen.basis.hash;

/**
 * @author <a href="mailto:wo@renzhen.la">haiker</a>
 * @version 2019-02-24 21:46
 */
public interface ServerSelector<S extends ServerNode> {
    boolean select(S serverNode);
}