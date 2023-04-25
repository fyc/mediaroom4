package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class EthernetDataBindingLandImpl extends EthernetDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.enable, 6);
        sViewsWithIds.put(R.id.disable, 7);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.EditText mboundView1;
    @NonNull
    private final android.widget.EditText mboundView2;
    @NonNull
    private final android.widget.EditText mboundView3;
    @NonNull
    private final android.widget.EditText mboundView4;
    @NonNull
    private final android.widget.EditText mboundView5;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers
    private android.databinding.InverseBindingListener mboundView1androidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of network.localIP
            //         is network.setLocalIP((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView1);
            // localize variables for thread safety
            // network
            com.sunmnet.mediaroom.tabsp.bean.NetworkBean network = mNetwork;
            // network != null
            boolean networkJavaLangObjectNull = false;
            // network.localIP
            java.lang.String networkLocalIP = null;



            networkJavaLangObjectNull = (network) != (null);
            if (networkJavaLangObjectNull) {




                network.setLocalIP(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private android.databinding.InverseBindingListener mboundView2androidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of network.gateway
            //         is network.setGateway((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView2);
            // localize variables for thread safety
            // network
            com.sunmnet.mediaroom.tabsp.bean.NetworkBean network = mNetwork;
            // network.gateway
            java.lang.String networkGateway = null;
            // network != null
            boolean networkJavaLangObjectNull = false;



            networkJavaLangObjectNull = (network) != (null);
            if (networkJavaLangObjectNull) {




                network.setGateway(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private android.databinding.InverseBindingListener mboundView3androidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of network.netmask
            //         is network.setNetmask((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView3);
            // localize variables for thread safety
            // network
            com.sunmnet.mediaroom.tabsp.bean.NetworkBean network = mNetwork;
            // network != null
            boolean networkJavaLangObjectNull = false;
            // network.netmask
            java.lang.String networkNetmask = null;



            networkJavaLangObjectNull = (network) != (null);
            if (networkJavaLangObjectNull) {




                network.setNetmask(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private android.databinding.InverseBindingListener mboundView4androidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of network.dns1
            //         is network.setDns1((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView4);
            // localize variables for thread safety
            // network
            com.sunmnet.mediaroom.tabsp.bean.NetworkBean network = mNetwork;
            // network != null
            boolean networkJavaLangObjectNull = false;
            // network.dns1
            java.lang.String networkDns1 = null;



            networkJavaLangObjectNull = (network) != (null);
            if (networkJavaLangObjectNull) {




                network.setDns1(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private android.databinding.InverseBindingListener mboundView5androidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of network.dns2
            //         is network.setDns2((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView5);
            // localize variables for thread safety
            // network
            com.sunmnet.mediaroom.tabsp.bean.NetworkBean network = mNetwork;
            // network.dns2
            java.lang.String networkDns2 = null;
            // network != null
            boolean networkJavaLangObjectNull = false;



            networkJavaLangObjectNull = (network) != (null);
            if (networkJavaLangObjectNull) {




                network.setDns2(((java.lang.String) (callbackArg_0)));
            }
        }
    };

    public EthernetDataBindingLandImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private EthernetDataBindingLandImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.Button) bindings[7]
            , (android.widget.Button) bindings[6]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.EditText) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.EditText) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.EditText) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (android.widget.EditText) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.EditText) bindings[5];
        this.mboundView5.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x40L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.network == variableId) {
            setNetwork((com.sunmnet.mediaroom.tabsp.bean.NetworkBean) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setNetwork(@Nullable com.sunmnet.mediaroom.tabsp.bean.NetworkBean Network) {
        updateRegistration(0, Network);
        this.mNetwork = Network;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.network);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeNetwork((com.sunmnet.mediaroom.tabsp.bean.NetworkBean) object, fieldId);
        }
        return false;
    }
    private boolean onChangeNetwork(com.sunmnet.mediaroom.tabsp.bean.NetworkBean Network, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.localIP) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        else if (fieldId == BR.gateway) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.netmask) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.dns1) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.dns2) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String networkGateway = null;
        java.lang.String networkDns2 = null;
        java.lang.String networkDns1 = null;
        com.sunmnet.mediaroom.tabsp.bean.NetworkBean network = mNetwork;
        java.lang.String networkLocalIP = null;
        java.lang.String networkNetmask = null;

        if ((dirtyFlags & 0x7fL) != 0) {


            if ((dirtyFlags & 0x45L) != 0) {

                    if (network != null) {
                        // read network.gateway
                        networkGateway = network.getGateway();
                    }
            }
            if ((dirtyFlags & 0x61L) != 0) {

                    if (network != null) {
                        // read network.dns2
                        networkDns2 = network.getDns2();
                    }
            }
            if ((dirtyFlags & 0x51L) != 0) {

                    if (network != null) {
                        // read network.dns1
                        networkDns1 = network.getDns1();
                    }
            }
            if ((dirtyFlags & 0x43L) != 0) {

                    if (network != null) {
                        // read network.localIP
                        networkLocalIP = network.getLocalIP();
                    }
            }
            if ((dirtyFlags & 0x49L) != 0) {

                    if (network != null) {
                        // read network.netmask
                        networkNetmask = network.getNetmask();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x43L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, networkLocalIP);
        }
        if ((dirtyFlags & 0x40L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView1, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView1androidTextAttrChanged);
            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView2, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView2androidTextAttrChanged);
            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView3, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView3androidTextAttrChanged);
            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView4, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView4androidTextAttrChanged);
            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView5, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView5androidTextAttrChanged);
        }
        if ((dirtyFlags & 0x45L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, networkGateway);
        }
        if ((dirtyFlags & 0x49L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, networkNetmask);
        }
        if ((dirtyFlags & 0x51L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, networkDns1);
        }
        if ((dirtyFlags & 0x61L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView5, networkDns2);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): network
        flag 1 (0x2L): network.localIP
        flag 2 (0x3L): network.gateway
        flag 3 (0x4L): network.netmask
        flag 4 (0x5L): network.dns1
        flag 5 (0x6L): network.dns2
        flag 6 (0x7L): null
    flag mapping end*/
    //end
}