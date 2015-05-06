# android-postfix-plugin
Android postfix plugin for AndroidStudio

![image](https://cloud.githubusercontent.com/assets/1386930/7448077/06d72a60-f24b-11e4-903f-99df2f5e92a8.gif)
![image](https://cloud.githubusercontent.com/assets/1386930/7448067/c8f2ceb6-f24a-11e4-8711-c5f2a5d205d4.gif)

Available templates:

|   Postfix Expression  | Description                                                                                                            | Example                                |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------- | -------------------------------------- |
| .toast        | Create and show Toast.                                                                              | Toast.makeText(context, expr, Toast.LENGTH_SHORT).show()                    |
| .log        | Logging. If there is constant variable "TAG", it use "TAG" . Else it use class name.                  | Log.d(TAG, expr)                    |
| .logd        | Logging. If BuildConfig.DEBUG is true, Log message.                 | if (BuildConfig.DEBUG) Log.d(TAG, expr)                    |

Plugin page
---------------
[JetBrains plugin page](https://plugins.jetbrains.com/plugin/7775)

Thanks
---------------
[guava-postfix-plugin](https://github.com/ukcrpb6/guava-postfix-plugin)


License
---------------
This project is released under the Apache License, Version 2.0.

* [The Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
