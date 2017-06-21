package com.mysuite.mytrade.archive.selector;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by jianl on 25/05/2017.
 */
public interface SecurityQuoteMessageSelector {
    public void select(final String filename) throws FileNotFoundException, UnsupportedEncodingException;
}
