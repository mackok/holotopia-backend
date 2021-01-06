package nhl.stenden.util;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

public class EMFGenerator extends UtilClass {

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
