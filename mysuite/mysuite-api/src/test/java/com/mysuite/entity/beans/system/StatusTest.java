package com.mysuite.entity.beans.system;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by jianl on 29/03/2017.
 */
public class StatusTest extends TestCase {

    @Test
    public void testToString(){
        Status status = new Status();
        status.setId(1L);
        status.setName("test");
        status.setCode(0);
        status.setVersion(1);
        assertEquals("com.mysuite.entity.beans.system.Status{id=1, name=test, code=0, version=1}\r\n",status.toString());
    }
}