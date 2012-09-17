package org.zfun;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * User: skuo Date: Mar 29, 2011
 */
public class MockitoTest {
    private class GreenTea<T> {
        public String describe() {
            //System.out.println("in describe()");
            return "green tea";
        }

        public String echo(String str) {
            //System.out.println(str);
            return str;
        }

        public int rating(int r) {
            //System.out.println(r);
            return r;
        }
        
        public String emit(T instance, Class<T> type) {
            return instance.toString();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGreenTea() {
        GreenTea<String> gt = new GreenTea<String>();
        String str = gt.describe();
        assertEquals("green tea", str);
        assertEquals("like green tea", gt.echo("like green tea"));
        assertEquals(10, gt.rating(10));
        assertEquals("more green tea", gt.emit("more green tea", String.class));

        // use mockito
        GreenTea<Integer> mockGt = mock(GreenTea.class);
        doReturn("mocked GreenTea.describe()").when(mockGt).describe();
        when(mockGt.echo(anyString())).thenAnswer(new Answer<String>() {
           public String answer(InvocationOnMock invocation) {
               Object[] args = invocation.getArguments();
               //Object mock = invocation.getMock();
               return (String) args[0];
           }
        });
        str = mockGt.describe();
        // unmocked method return default: null for object and 0 for int
        assertEquals("mocked GreenTea.describe()", str);
        str = mockGt.echo("mock green tea");
        assertEquals("mock green tea", str);
        int r = mockGt.rating(10);
        assertEquals(0, r);
    }
    
    @SuppressWarnings({"unchecked", "rawtypes" })
    @Test
    public void testVerify() {
        // mock creation and stubbing
        List mockList = mock(List.class);
        when(mockList.get(anyInt())).thenReturn("element");
        
        // use mock object
        mockList.add("one");
        mockList.add("none");
        mockList.clear();
        mockList.get(20);
        mockList.add(Integer.valueOf(0));
        
        // verify
        verify(mockList).add("one");
        verify(mockList).clear();
        verify(mockList).add("none");
        verify(mockList).get(anyInt());
        
        verify(mockList, times(1)).get(anyInt());
        verify(mockList, times(2)).add(isA(String.class));
        verify(mockList, times(1)).add(isA(Integer.class));
        verify(mockList, times(3)).add(any(String.class));
    }
}