cmake_minimum_required(VERSION 3.22.1)

project("nativecalc")

add_library(
        nativecalc
        SHARED
        native-calc.cpp
)

find_library(log-lib log)

target_link_libraries(
        nativecalc
        ${log-lib}
)
