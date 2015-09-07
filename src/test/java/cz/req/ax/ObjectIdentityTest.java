package cz.req.ax;

import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Id;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 7.9.15
 */
public class ObjectIdentityTest {


    @Test
    public void testBasic() {
        testNullAndValue(new Basic());
    }

    @Test
    public void testExtend() {
        testNullAndValue(new Extend());
    }

    private void testNullAndValue(Basic basic) {
        basic.setIdExt(12);
        basic.setText("asd");
        Assert.assertNull(ObjectIdentity.id(basic));
        basic.setId(1);
        Assert.assertEquals(Integer.valueOf(1), ObjectIdentity.id(basic));
    }

    public static class Basic {
        Integer idExt;
        @Id
        Integer id;
        String text = "xxx";

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getIdExt() {
            return idExt;
        }

        public void setIdExt(Integer idExt) {
            this.idExt = idExt;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class Extend extends Basic {

        Integer num;

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }

}