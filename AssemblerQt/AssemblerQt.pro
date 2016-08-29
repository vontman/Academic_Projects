QT += core
QT -= gui

TARGET = AssemblerQt
CONFIG += console
CONFIG -= app_bundle

TEMPLATE = app

SOURCES += main.cpp \
    regexbuilder.cpp \
    parser.cpp \
    instruction.cpp \
    pass1.cpp

HEADERS += \
    regexbuilder.h \
    parser.h \
    instruction.h \
    pass1.h
INCLUDEPATH += $$PWD/Headers
CONFIG += c++11

DISTFILES += \
    ../../../media/alythabet/587AA34C7AA3262A/CSED/2.2/Systems Programming/Final Project/Assembler Parser/optable.txt \
    optable.txt \
    SIC1-3.asm \
    LISA.txt
