set engine_home=C:\Code\Ballmaster-Engine
set game_home=C:\Code\Ballmaster-Game
set java_bin=C:\Code\jdk-24.0.1\bin
set java_out=%engine_home%\tmp
set java_src=%engine_home%\src\com\app\engine\*.java
set jar_name=engine.jar
set jar_out=%java_out%\%jar_name%

REM build
%java_bin%\javac -d %java_out% %java_src%
%java_bin%\jar cf %jar_out% -C %java_out% .

REM cleanup
xcopy %jar_out% %game_home%\%jar_name%*
rmdir /s /q %java_out%
