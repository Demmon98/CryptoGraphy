import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Arquillian.class)
public class DESTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(DES.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    DES des = new DES();


    @Test
    public void encryptDecrypt() {
        String s = "some string";
        String encrypt = des.encrypt(s, "passwrd", "randomTx");
        assertNotEquals(s, encrypt);
        String d = des.decrypt(encrypt, "passwrd", "randomTx");
        assertEquals(s, d);
    }

}
