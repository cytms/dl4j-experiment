package org.deeplearning4j.examples.recurrent.word2vecproduct;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.WordDictionary;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cytms on 6/30/16.
 */
public class JiebaTokenizer {
    public static JiebaTokenizer instance;
    JiebaSegmenter segmenter = new JiebaSegmenter();

    public static JiebaTokenizer getInstance() {
        if (instance == null)
            instance = new JiebaTokenizer();
        return instance;
    }

    public JiebaTokenizer() {
        WordDictionary dict = WordDictionary.getInstance();
//        Path path = FileSystems.getDefault().getPath("/Users/cytms/Projects/ec_title_quality_api/src/main/resources/dataSource/dict/query_logs.txt");
//        dict.loadUserDict(path);

        //"/Users/cytms/Projects/ec_title_quality_api/src/main/resources/dataSource/dict/au_cat.txt"
        Path path = FileSystems.getDefault().getPath(new File("src/main/resources/dataSource/dict/au_cat.txt").getAbsolutePath());
        dict.loadUserDict(path);

        path = FileSystems.getDefault().getPath(new File("src/main/resources/dataSource/dict/st_cat.txt").getAbsolutePath());
        dict.loadUserDict(path);

        path = FileSystems.getDefault().getPath(new File("src/main/resources/dataSource/dict/sh_cat.txt").getAbsolutePath());
        dict.loadUserDict(path);
    }

    public List<String> getJiebaTokens(String title) {
        List<SegToken> cuts = segmenter.process(title.replace("&amp;", " ").replaceAll("[#+【】！!,.（）\\(\\)？，、。：「」；…*~～的了及\\- ]+", "_").trim(), JiebaSegmenter.SegMode.SEARCH);

        List<String> tokens = new ArrayList<>();

        for (SegToken cut : cuts) {
            String word = cut.word;
            if (!word.trim().equals("_"))
                tokens.add(word.replace("_", " "));
        }

        return tokens;
    }
}
