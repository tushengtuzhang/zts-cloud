package com.zts.service;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.IIOAdapter;
import com.hankcs.hanlp.corpus.io.ResourceIOAdapter;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.utility.Predefine;
import com.hankcs.hanlp.utility.TextUtility;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static com.zts.service.OTNlp.Config.CompanyDicRoot;


/**
 * Created by Lycan on 2017/9/11.
 */
public class OTNlp {

    private OTNlp() {
    }

    public static final class Config {
        public static boolean DEBUG = false;
        public static IIOAdapter IOAdapter = new ResourceIOAdapter();
        public static String CompanyDicRoot = "OTCompany/";

        public Config() {
        }

        public static void enableDebug() {
            enableDebug(true);
        }

        public static void enableDebug(boolean enable) {
            DEBUG = enable;
            if (DEBUG) {
                Predefine.logger.setLevel(Level.ALL);
            } else {
                Predefine.logger.setLevel(Level.OFF);
            }

        }

        static {
            Properties p = new Properties();

            int i;
            int lastSplash;
            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                if (loader == null) {
                    loader = HanLP.Config.class.getClassLoader();
                }

                p.load(new InputStreamReader((InputStream) (loader.getResourceAsStream("otalknlp.properties")), "UTF-8"));
                String root = p.getProperty("root", "").replaceAll("\\\\", "/");
                if (root.length() > 0 && !root.endsWith("/")) {
                    root = root + '/';
                }

                CompanyDicRoot = root + p.getProperty("companyRoot", CompanyDicRoot);
                if (!CompanyDicRoot.endsWith("/")) {
                    CompanyDicRoot = CompanyDicRoot + '/';
                }

                FileOperator.createDirectory(CompanyDicRoot);

                String ioAdapterClassName = p.getProperty("IOAdapter");
                if (ioAdapterClassName != null) {
                    try {
                        Class<?> clazz = Class.forName(ioAdapterClassName);
                        Constructor<?> ctor = clazz.getConstructor();
                        Object instance = ctor.newInstance();
                        IOAdapter = (IIOAdapter) instance;
                    } catch (ClassNotFoundException var9) {
                        Predefine.logger.warning(String.format("找不到IO适配器类： %s ，请检查第三方插件jar包", new Object[]{ioAdapterClassName}));
                    } catch (NoSuchMethodException var10) {
                        Predefine.logger.warning(String.format("工厂类[%s]没有默认构造方法，不符合要求", new Object[]{ioAdapterClassName}));
                    } catch (SecurityException var11) {
                        Predefine.logger.warning(String.format("工厂类[%s]默认构造方法无法访问，不符合要求", new Object[]{ioAdapterClassName}));
                    } catch (Exception var12) {
                        Predefine.logger.warning(String.format("工厂类[%s]构造失败：%s\n", new Object[]{ioAdapterClassName, TextUtility.exceptionToString(var12)}));
                    }
                }
            } catch (Exception var13) {
                StringBuilder sbInfo = new StringBuilder("========Tips========\n请将otalknlp.properties放在下列目录：\n");
                String classPath = (String) System.getProperties().get("java.class.path");
                if (classPath != null) {
                    String[] var4 = classPath.split(File.pathSeparator);
                    i = var4.length;

                    for (lastSplash = 0; lastSplash < i; ++lastSplash) {
                        String path = var4[lastSplash];
                        if ((new File(path)).isDirectory()) {
                            sbInfo.append(path).append('\n');
                        }
                    }
                }

                sbInfo.append("Web项目则请放到下列目录：\nWebapp/WEB-INF/lib\nWebapp/WEB-INF/classes\nAppserver/lib\nJRE/lib\n");
                sbInfo.append("并且编辑root=PARENT/path/to/your/data\n");
                sbInfo.append("现在HanLP将尝试从jar包内部resource读取data……");
                Predefine.logger.info("otalknlp.properties，进入portable模式。若需要自定义HanLP，请按下列提示操作：\n" + sbInfo);
            }

        }
    }

    public static List<Term> segment(Integer companyId, String text) {
        if (getCompanyDic(companyId) == null || !new File(getCompanyDic(companyId)).exists()){
            return HanLP.segment(text);
        }
        return OTTokenizer.getInstance().segment(text, getCompanyDic(companyId));
    }

    public static String getCompanyDic(Integer companyId) {
        if (companyId == null) {
            Predefine.logger.warning("公司ID为null,将使用core词典CoreCompanyDic.txt\n");
            return null;
        }
        return CompanyDicRoot + companyId + ".txt";
    }

    public static void main(String args[]) {
//        OTNlp.segment(1, "P10p8微观经济学继续教育循环经济，我要住北京酒店");
//        OTNlp.segment(1017, "P10p8微观经济学继续教育循环经济，我要住北京酒店");
    }
}
