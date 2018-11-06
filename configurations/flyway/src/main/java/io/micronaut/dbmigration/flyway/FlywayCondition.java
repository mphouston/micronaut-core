package io.micronaut.dbmigration.flyway;

import io.micronaut.context.BeanContext;
import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;
import io.micronaut.core.annotation.AnnotationMetadataProvider;
import io.micronaut.core.naming.NameResolver;
import io.micronaut.inject.qualifiers.Qualifiers;

import java.util.Optional;

/**
 * Condition used to create {@link org.flywaydb.core.Flyway} beans. Only enabled and valid Flyway configurations
 * will enable the creation of the Flyway bean.
 *
 * @author Iván López
 * @since 1.1
 */
public class FlywayCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context) {
        BeanContext beanContext = context.getBeanContext();
        AnnotationMetadataProvider component = context.getComponent();

        if (component instanceof NameResolver) {
            Optional<String> name = ((NameResolver) component).resolveName();

            if (name.isPresent()) {
                Optional<FlywayConfigurationProperties> optionConfig = beanContext.findBean(FlywayConfigurationProperties.class, Qualifiers.byName(name.get()));

                if (optionConfig.isPresent()) {
                    FlywayConfigurationProperties config = optionConfig.get();

                    if (config.getDataSource() == null && !config.hasAlternativeDatabaseConfiguration()) {
                        context.fail("Flyway bean not created for identifier \"" + name.get() + "\" because no data source found");
                        return false;
                    }

                    if (!config.isEnabled()) {
                        context.fail("Flyway bean not created for identifier \"" + name.get() + "\" because flyway configuration is disabled");
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
