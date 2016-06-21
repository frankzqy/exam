package org.inspur.exam.util;

import java.util.TreeSet;

public class DictUtil {
    public static TreeSet<String> dictSet;
    static {
	initDict();
    }
    public static void initDict(){
/*	IParticipleSercive participleSercive = SpringContextHolder.getBean("participleSercive");
	dictSet = participleSercive.getDictSet();*/
    }
}
