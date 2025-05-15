engine_home="/Users/stallion/Code/Ballmaster-Engine"
game_home="/Users/stallion/Code/Ballmaster-Game"
java_bin="$engine_home/runtime/jdk-23.0.2.jdk/Contents/Home/bin"
java_out="$engine_home/tmp"
java_src="$engine_home/src/com/app/engine/*.java"
jar_name="engine.jar"
jar_out="$java_out/$jar_name"

#prep() {
#  rm -rf $java_out
#}

build() {
  $java_bin/javac -d $java_out $java_src
  $java_bin/jar cf $jar_out -C $java_out .
}

cleanup() {
  cp $jar_out $game_home/$jar_name
  rm -rf $java_out
}

#prep
build
cleanup
