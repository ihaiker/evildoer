package la.renzhen.test;

import la.renzhen.basis.hash.ConsistentHash;
import la.renzhen.basis.hash.ServerNode;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:wo@renzhen.la">haiker</a>
 * @version 2019-02-24 18:03
 */
public class TestConsistentHash {

    public class Service implements ServerNode {
        private String name;
        public Service(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public long hashCode(int virtualNum) {
            String s = String.format("%s&&VN%03d", name, virtualNum);
            return s.hashCode();
        }
    }


    private ConsistentHash<Service> consistentHash = new ConsistentHash<Service>(100);

    @Before
    public void init() {
        for (int i = 0; i < 6; i++) {
            consistentHash.addServerNode(new Service(String.format("%03d", i)));
        }
    }

    @Test
    public void testHash() {
        Optional<Map.Entry<Integer, Service>> entry = consistentHash.getFirstServerNode();
        assert entry.isPresent();

        Optional<Map.Entry<Integer, Service>> last = consistentHash.getLastServerNode();
        assert last.isPresent();

        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            Optional<Service> optional = consistentHash.getFirstServerNode(String.format("%08d", i), null);
            assert optional.isPresent();
            String name = optional.get().getName();
            int num = map.getOrDefault(name, 0);
            num++;
            map.put(name, num);
        }
        System.out.println(map);
        assert map.get("000") > 0;

        Optional<Service> optional = consistentHash.getFirstServerNode(String.format("%08d", 1), serverNode -> serverNode.name.equals("000"));
        assert optional.isPresent() && optional.get().getName().equals("000");
    }
}
