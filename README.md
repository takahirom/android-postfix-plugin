# android-postfix-plugin
![build status](https://circleci.com/gh/takahirom/android-postfix-plugin/tree/master.svg?style=shield&circle-token=d7e574ba58679594e235b3ca326ef683eee7bf6f)  

Android postfix plugin for AndroidStudio

![image](https://cloud.githubusercontent.com/assets/1386930/10118005/8d45e898-64a6-11e5-8c32-8f38b0105177.gif)
![image](https://cloud.githubusercontent.com/assets/1386930/7507612/3c131392-f4ad-11e4-98a0-e56dbfab8c69.gif)
![image](https://cloud.githubusercontent.com/assets/1386930/7448067/c8f2ceb6-f24a-11e4-8711-c5f2a5d205d4.gif)

Available templates:

|   Postfix Expression  | Description                                                                                                            | Example                                |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------- | -------------------------------------- |
| .toast        | Create and show Toast.                                                                              | Toast.makeText(context, expr, Toast.LENGTH_SHORT).show()                    |
| .log        | Logging. If there is constant variable "TAG", it use "TAG" . Else it use class name.                  | Log.d(TAG, expr)                    |
| .logd        | Logging. If BuildConfig.DEBUG is true, Log message.                 | if (BuildConfig.DEBUG) Log.d(TAG, expr)                    |
| .find | Typed FindView | (ViewType) findViewById(expr) |
| .isemp        | Check empty.                | TextUtils.isEmpty(expr)                    |
| .vg        | Divide view visible or gone.                | (expr) ? View.VISIBLE : View.GONE                    |

Plugin page
---------------
[JetBrains plugin page](https://plugins.jetbrains.com/plugin/7775)


Contributors
---------------
* [kikuchy](http://www.github.com/kikuchy)
* [Axlchen](http://www.github.com/Axlchen)

How to run the repository code
----------------
1. Clone
2. Run Gradle Wrapper
In project root.
```
./gradlew runIdea
```

Thanks
---------------
[guava-postfix-plugin](https://github.com/ukcrpb6/guava-postfix-plugin)


License
---------------
This project is released under the Apache License, Version 2.0.

* [The Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
