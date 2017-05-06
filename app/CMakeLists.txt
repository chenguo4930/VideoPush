
cmake_minimum_required(VERSION 3.4.1)
find_library( log-lib
              log )

find_library( android-lib
              android)

add_library( livelib
             SHARED
             src/main/cpp/pushvideo.c
             src/main/cpp/queue.c )
#变量
set(distribution_DIR ../../../../libs)

add_library( x264lib
             STATIC
             IMPORTED)
set_target_properties( x264lib
                       PROPERTIES IMPORTED_LOCATION
                       ${distribution_DIR}/x264/libx264.a)

add_library( faaclib
             STATIC
             IMPORTED)
set_target_properties( faaclib
                       PROPERTIES IMPORTED_LOCATION
                       ${distribution_DIR}/faac/libfaac.a)

add_library( rtmpdumplib
             STATIC
             IMPORTED)
set_target_properties( rtmpdumplib
                       PROPERTIES IMPORTED_LOCATION
                       ${distribution_DIR}/rtmpdump/librtmp.a)

#设置编译C的标准
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")
include_directories(src/main/cpp)
include_directories(libs)

target_link_libraries( livelib
                       rtmpdumplib
                       faaclib
                       x264lib
                       ${android-lib}
                       ${log-lib} )