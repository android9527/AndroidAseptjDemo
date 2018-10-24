package io.github.android9527.apt_api;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.View;

public class APTButterKnife
{

    private static final String SUFFIX = "_ViewInject";

    /**
     * BindView annotated fields and methods in the specified {@link Activity}. The current content
     * view is used as the view root.
     *
     * @param target Target activity for view binding.
     */
    @UiThread
    public static void bind(@NonNull Activity target)
    {
        View sourceView = target.getWindow().getDecorView();
        createBinding(target, sourceView);
    }

    /**
     * BindView annotated fields and methods in the specified {@link View}. The view and its children
     * are used as the view root.
     *
     * @param target Target view for view binding.
     */
    @UiThread
    public static void bind(@NonNull View target)
    {
        createBinding(target, target);
    }

    /**
     * BindView annotated fields and methods in the specified {@code target} using the {@code source}
     * {@link Activity} as the view root.
     *
     * @param target Target class for view binding.
     * @param source Activity on which IDs will be looked up.
     */
    @UiThread
    public static void bind(@NonNull Object target, @NonNull View source)
    {
        createBinding(target, source);
    }

    public static void createBinding(@NonNull Object target, @NonNull View root)
    {
        try
        {
            //生成类名+后缀名的代理类，并执行注入操作
            Class<?> targetClass = target.getClass();
            Class<?> proxyClass = Class.forName(targetClass.getName() + SUFFIX);
            ViewInject proxy = (ViewInject) proxyClass.newInstance();
            proxy.inject(target, root);

        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

}