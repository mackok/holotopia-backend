package nhl.stenden.util;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Class that handles generating an entity manager factory.
 */
public class EMFGenerator extends UtilClass {

    /**
     * Generates an entity manager factory.
     * @param dataSource the data source that should be used
     * @param additionalProperties additional properties that should be added
     * @return a generated entity manager factory
     */
    public static LocalContainerEntityManagerFactoryBean generate(DataSource dataSource, Properties additionalProperties){
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("nhl.stenden.model");
        em.setDataSource(dataSource);
        em.setPersistenceUnitName("RC_Unit");
        em.setJpaProperties(additionalProperties);
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }
}
