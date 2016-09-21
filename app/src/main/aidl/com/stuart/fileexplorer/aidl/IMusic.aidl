// IMusic.aidl
package com.stuart.fileexplorer.aidl;

// Declare any non-default types here with import statements

interface IMusic {

            /**
            *  播放
            */
            void start();

            /**
            *  停止
            */
            void stop();

            /**
            *  暂停
            */
            void pause();

            /**
            *  下一曲
            */
            void next();

            /**
            *  上一曲
            */
            void pre();

            /**
            *  跳转到
            */
            void seekTo(long time);
}
