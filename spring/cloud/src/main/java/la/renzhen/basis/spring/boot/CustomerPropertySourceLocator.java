package la.renzhen.basis.spring.boot;

import org.springframework.boot.env.EnumerableCompositePropertySource;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomerPropertySourceLocator implements PropertySourceLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {
        EnumerableCompositePropertySource source = new EnumerableCompositePropertySource("evildoer");
        //随机端口
        source.add(new PortPropertySource());

        //自定义属性
        Map<String, Object> attr = new HashMap<String, Object>();
        attr.put("evildoer.author.name", "haiker");
        attr.put("evildoer.author.mail", "wo@renzhen.la");
        source.add(new MapPropertySource("customProperty", attr));
        return source;
    }
}
