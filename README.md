# android-postfix-plugin
Android postfix plugin for AndroidStudio

![image](https://cloud.githubusercontent.com/assets/1386930/7507612/3c131392-f4ad-11e4-98a0-e56dbfab8c69.gif)
![image](https://cloud.githubusercontent.com/assets/1386930/7448067/c8f2ceb6-f24a-11e4-8711-c5f2a5d205d4.gif)

Available templates:

|   Postfix Expression  | Description                                                                                                            | Example                                |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------- | -------------------------------------- |
| .toast        | Create and show Toast.                                                                              | Toast.makeText(context, expr, Toast.LENGTH_SHORT).show()                    |
| .log        | Logging. If there is constant variable "TAG", it use "TAG" . Else it use class name.                  | Log.d(TAG, expr)                    |
| .logd        | Logging. If BuildConfig.DEBUG is true, Log message.                 | if (BuildConfig.DEBUG) Log.d(TAG, expr)                    |
| .isemp        | Check empty.                | TextUtils.isEmpty(expr)                    |

Plugin page
---------------
[JetBrains plugin page](https://plugins.jetbrains.com/plugin/7775)


Contributors
---------------
* [kikuchy](http://www.github.com/kikuchy)

How to run the repository code
----------------
1. Clone
2. Run Gradle Wrapper
In project root.
```
./gradlew runIdea
```

Contribution
----------------
1. Fork it ( http://github.com/takahirom/android-postfix-plugin/fork )
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -am 'Add some feature')
4. Push to the branch (git push origin my-new-feature)
5. Create new Pull Request


Thanks
---------------
[guava-postfix-plugin](https://github.com/ukcrpb6/guava-postfix-plugin)


License
---------------
This project is released under the Apache License, Version 2.0.

* [The Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
