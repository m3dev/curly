package curly;

import curly.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RequestTest {

    @Test
    public void type() throws Exception {
        assertThat(curly.Request.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        curly.Request target = new curly.Request("https://github.com/seratch");
        assertThat(target, notNullValue());
    }

    @Test
    public void getHeader_A$String() throws Exception {
        curly.Request target = new curly.Request("https://github.com/seratch");
        String name = "Connection";
        String actual = target.getHeader(name);
        String expected = "keep-alive";
        assertThat(actual, is(equalTo(expected)));
    }

}
