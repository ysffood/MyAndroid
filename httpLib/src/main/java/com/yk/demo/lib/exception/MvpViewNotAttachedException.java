package com.yk.demo.lib.exception;

/**
 * presenter未获取mvpView Exception
 * @author yk
 * @version
 * created at 2016/11/25 11:01
 */
public class MvpViewNotAttachedException extends RuntimeException{

    public MvpViewNotAttachedException() {
        super("Please call Presenter.attachView(MvpView) before" +
                " requesting data to the Presenter");
    }

}
