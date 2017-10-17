##Kotlin parser

This is a sample of a kotlin code parser lib ! 


## how to use:
To use it, build in local then  add to your pom.xml the dependencies

```
  <dependency>
            <groupId>com.github.sarahbuisson</groupId>
            <artifactId>kotlin-parser</artifactId>
            <version>1.0-SNAPSHOT</version>
  </dependency>
```

create a listener ( a class who well react each time a statement of the kotlin file is parsed, IE:)
```


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

```


Then add to your code:

```
    KotlinLexer KotlinLexer = new KotlinLexer(CharStreams.fromStream(getClass().getResourceAsStream("./sampleKotlin.kt")));

    CommonTokenStream commonTokenStream = new CommonTokenStream(KotlinLexer);
    KotlinParser kotlinParser = new KotlinParser(commonTokenStream);

    ParseTree tree = kotlinParser.kotlinFile();
    ParseTreeWalker walker = new ParseTreeWalker();

    ClassCounterListener listener = new ClassCounterListener();
    walker.walk(listener, tree);

```
