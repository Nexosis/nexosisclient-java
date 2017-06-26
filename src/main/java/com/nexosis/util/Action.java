package com.nexosis.util;

public interface Action<T1, T2>
{
    void invoke(T1 target1, T2 target2) throws Exception;
}

