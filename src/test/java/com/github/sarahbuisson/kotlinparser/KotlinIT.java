package com.github.sarahbuisson.kotlinparser;

import com.github.sarahbuisson.kotlinparser.KotlinLexer;
import com.github.sarahbuisson.kotlinparser.KotlinParser;
import com.github.sarahbuisson.kotlinparser.KotlinParserBaseListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class KotlinIT {

    @Test
    public void shouldCount2Class() throws IOException {
        //GIVEN
        KotlinLexer KotlinLexer = new KotlinLexer(CharStreams.fromStream(getClass().getResourceAsStream("./sampleKotlin.kt")));

        CommonTokenStream commonTokenStream = new CommonTokenStream(KotlinLexer);
        KotlinParser kotlinParser = new KotlinParser(commonTokenStream);

        ParseTree tree = kotlinParser.kotlinFile();
        ParseTreeWalker walker = new ParseTreeWalker();


        //WHEN
        ClassCounterListener listener = new ClassCounterListener();
        walker.walk(listener, tree);

        //THEN
        Assert.assertEquals(2, listener.getNumberOfClass());
        Assert.assertEquals(1, listener.getNumberOfDataClass());
        Assert.assertEquals(1, listener.getNumberOfClassModifier());


    }


    class ClassCounterListener extends KotlinParserBaseListener {

        int numberOfClassModifier;
        int numberOfDataClass;
        int numberOfClass;


        @Override
        public void enterClassModifier(KotlinParser.ClassModifierContext ctx) {
            numberOfClassModifier++;
            if (ctx.DATA() != null) {
                numberOfDataClass++;
            }
        }

        @Override
        public void enterClassDeclaration(KotlinParser.ClassDeclarationContext ctx) {
            if (ctx.CLASS() != null) {
                numberOfClass++;
            }
        }

        /**
         * Getter for property 'numberOfClassModifier'.
         *
         * @return Value for property 'numberOfClassModifier'.
         */
        public int getNumberOfClassModifier() {
            return numberOfClassModifier;
        }

        /**
         * Getter for property 'numberOfDataClass'.
         *
         * @return Value for property 'numberOfDataClass'.
         */
        public int getNumberOfDataClass() {
            return numberOfDataClass;
        }

        /**
         * Getter for property 'numberOfClass'.
         *
         * @return Value for property 'numberOfClass'.
         */
        public int getNumberOfClass() {
            return numberOfClass;
        }
    }


}
