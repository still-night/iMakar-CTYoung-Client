// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.actionbarsherlock.internal.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.ImageButton;
import com.actionbarsherlock.internal.ResourcesCompat;
import com.actionbarsherlock.internal.view.View_HasStateListenerSupport;
import com.actionbarsherlock.internal.view.View_OnAttachStateChangeListener;
import com.actionbarsherlock.view.ActionProvider;
import com.actionbarsherlock.view.MenuItem;
import java.util.*;

// Referenced classes of package com.actionbarsherlock.internal.view.menu:
//            BaseMenuPresenter, ActionMenuView, ActionMenuItemView, MenuBuilder, 
//            MenuItemImpl, MenuPopupHelper, SubMenuBuilder, MenuView

public class ActionMenuPresenter extends BaseMenuPresenter
    implements com.actionbarsherlock.view.ActionProvider.SubUiVisibilityListener
{
    private class ActionButtonSubmenu extends MenuPopupHelper
    {

        public void onDismiss()
        {
            super.onDismiss();
            mActionButtonPopup = null;
            mOpenSubMenuId = 0;
        }

        final ActionMenuPresenter this$0;

        public ActionButtonSubmenu(Context context, SubMenuBuilder submenubuilder)
        {
            this$0 = ActionMenuPresenter.this;
            super(context, submenubuilder);
            int i;
            if(!((MenuItemImpl)submenubuilder.getItem()).isActionButton())
            {
                int j;
                boolean flag;
                boolean flag1;
                if(mOverflowButton == null)
                    context = (View)mMenuView;
                else
                    context = mOverflowButton;
                setAnchorView(context);
            }
            setCallback(mPopupPresenterCallback);
            flag1 = false;
            j = submenubuilder.size();
            i = 0;
            do
            {
label0:
                {
                    flag = flag1;
                    if(i < j)
                    {
                        actionmenupresenter = submenubuilder.getItem(i);
                        if(!isVisible() || getIcon() == null)
                            break label0;
                        flag = true;
                    }
                    setForceShowIcon(flag);
                    return;
                }
                i++;
            } while(true);
        }
    }

    private static class HasPermanentMenuKey
    {

        public static boolean get(Context context)
        {
            return ViewConfiguration.get(context).hasPermanentMenuKey();
        }

        private HasPermanentMenuKey()
        {
        }
    }

    private class OpenOverflowRunnable
        implements Runnable
    {

        public void run()
        {
            mMenu.changeMenuMode();
            View view = (View)mMenuView;
            if(view != null && view.getWindowToken() != null && mPopup.tryShow())
                mOverflowPopup = mPopup;
            mPostedOpenRunnable = null;
        }

        private OverflowPopup mPopup;
        final ActionMenuPresenter this$0;

        public OpenOverflowRunnable(OverflowPopup overflowpopup)
        {
            this$0 = ActionMenuPresenter.this;
            super();
            mPopup = overflowpopup;
        }
    }

    private class OverflowMenuButton extends ImageButton
        implements ActionMenuView.ActionMenuChildView, View_HasStateListenerSupport
    {

        public void addOnAttachStateChangeListener(View_OnAttachStateChangeListener view_onattachstatechangelistener)
        {
            mListeners.add(view_onattachstatechangelistener);
        }

        public boolean needsDividerAfter()
        {
            return false;
        }

        public boolean needsDividerBefore()
        {
            return false;
        }

        protected void onAttachedToWindow()
        {
            super.onAttachedToWindow();
            for(Iterator iterator = mListeners.iterator(); iterator.hasNext(); ((View_OnAttachStateChangeListener)iterator.next()).onViewAttachedToWindow(this));
        }

        protected void onDetachedFromWindow()
        {
            super.onDetachedFromWindow();
            for(Iterator iterator = mListeners.iterator(); iterator.hasNext(); ((View_OnAttachStateChangeListener)iterator.next()).onViewDetachedFromWindow(this));
            if(mOverflowPopup != null)
                mOverflowPopup.dismiss();
        }

        public boolean performClick()
        {
            if(super.performClick())
            {
                return true;
            } else
            {
                playSoundEffect(0);
                showOverflowMenu();
                return true;
            }
        }

        public void removeOnAttachStateChangeListener(View_OnAttachStateChangeListener view_onattachstatechangelistener)
        {
            mListeners.remove(view_onattachstatechangelistener);
        }

        private final Set mListeners = new HashSet();
        final ActionMenuPresenter this$0;

        public OverflowMenuButton(Context context)
        {
            this$0 = ActionMenuPresenter.this;
            super(context, null, com.actionbarsherlock.R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
        }
    }

    private class OverflowPopup extends MenuPopupHelper
    {

        public void onDismiss()
        {
            super.onDismiss();
            mMenu.close();
            mOverflowPopup = null;
        }

        final ActionMenuPresenter this$0;

        public OverflowPopup(Context context, MenuBuilder menubuilder, View view, boolean flag)
        {
            this$0 = ActionMenuPresenter.this;
            super(context, menubuilder, view, flag);
            setCallback(mPopupPresenterCallback);
        }
    }

    private class PopupPresenterCallback
        implements MenuPresenter.Callback
    {

        public void onCloseMenu(MenuBuilder menubuilder, boolean flag)
        {
            if(menubuilder instanceof SubMenuBuilder)
                ((SubMenuBuilder)menubuilder).getRootMenu().close(false);
        }

        public boolean onOpenSubMenu(MenuBuilder menubuilder)
        {
            if(menubuilder == null)
            {
                return false;
            } else
            {
                mOpenSubMenuId = ((SubMenuBuilder)menubuilder).getItem().getItemId();
                return false;
            }
        }

        final ActionMenuPresenter this$0;

        private PopupPresenterCallback()
        {
            this$0 = ActionMenuPresenter.this;
            super();
        }

    }

    private static class SavedState
        implements Parcelable
    {

        public int describeContents()
        {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i)
        {
            parcel.writeInt(openSubMenuId);
        }

        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

            public SavedState createFromParcel(Parcel parcel)
            {
                return new SavedState(parcel);
            }

            public volatile Object createFromParcel(Parcel parcel)
            {
                return createFromParcel(parcel);
            }

            public SavedState[] newArray(int i)
            {
                return new SavedState[i];
            }

            public volatile Object[] newArray(int i)
            {
                return newArray(i);
            }

        }
;
        public int openSubMenuId;


        SavedState()
        {
        }

        SavedState(Parcel parcel)
        {
            openSubMenuId = parcel.readInt();
        }
    }


    public ActionMenuPresenter(Context context)
    {
        super(context, com.actionbarsherlock.R.layout.abs__action_menu_layout, com.actionbarsherlock.R.layout.abs__action_menu_item_layout);
    }

    private View findViewForItem(MenuItem menuitem)
    {
        ViewGroup viewgroup = (ViewGroup)mMenuView;
        if(viewgroup != null) goto _L2; else goto _L1
_L1:
        View view = null;
_L6:
        return view;
_L2:
        int i;
        int j;
        j = viewgroup.getChildCount();
        i = 0;
_L7:
        if(i >= j) goto _L4; else goto _L3
_L3:
        View view1;
        view1 = viewgroup.getChildAt(i);
        if(!(view1 instanceof MenuView.ItemView))
            continue; /* Loop/switch isn't completed */
        view = view1;
        if(((MenuView.ItemView)view1).getItemData() == menuitem) goto _L6; else goto _L5
_L5:
        i++;
          goto _L7
_L4:
        return null;
    }

    public static boolean reserveOverflow(Context context)
    {
        if(android.os.Build.VERSION.SDK_INT >= 14) goto _L2; else goto _L1
_L1:
        if(android.os.Build.VERSION.SDK_INT < 11) goto _L4; else goto _L3
_L3:
        return true;
_L4:
        return false;
_L2:
        if(HasPermanentMenuKey.get(context))
            return false;
        if(true) goto _L3; else goto _L5
_L5:
    }

    public void bindItemView(MenuItemImpl menuitemimpl, MenuView.ItemView itemview)
    {
        itemview.initialize(menuitemimpl, 0);
        menuitemimpl = (ActionMenuView)mMenuView;
        ((ActionMenuItemView)itemview).setItemInvoker(menuitemimpl);
    }

    public boolean dismissPopupMenus()
    {
        return hideOverflowMenu() | hideSubMenus();
    }

    public boolean filterLeftoverView(ViewGroup viewgroup, int i)
    {
        if(viewgroup.getChildAt(i) == mOverflowButton)
            return false;
        else
            return super.filterLeftoverView(viewgroup, i);
    }

    public boolean flagActionItems()
    {
        int k;
        int i1;
        int j1;
        int l1;
        int i3;
        int j3;
        int l3;
        int i4;
        ArrayList arraylist;
        ViewGroup viewgroup;
        SparseBooleanArray sparsebooleanarray;
        boolean flag1;
label0:
        {
            arraylist = mMenu.getVisibleItems();
            l3 = arraylist.size();
            int i = mMaxItems;
            j3 = mActionItemWidthLimit;
            i4 = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
            viewgroup = (ViewGroup)mMenuView;
            i1 = 0;
            int k1 = 0;
            flag1 = false;
            boolean flag = false;
            j1 = 0;
            while(j1 < l3) 
            {
                MenuItemImpl menuitemimpl = (MenuItemImpl)arraylist.get(j1);
                int j2;
                if(menuitemimpl.requiresActionButton())
                    i1++;
                else
                if(menuitemimpl.requestsActionButton())
                    k1++;
                else
                    flag = true;
                j2 = i;
                if(mExpandedActionViewsExclusive)
                {
                    j2 = i;
                    if(menuitemimpl.isActionViewExpanded())
                        j2 = 0;
                }
                j1++;
                i = j2;
            }
            j1 = i;
            if(!mReserveOverflow)
                break label0;
            if(!flag)
            {
                j1 = i;
                if(i1 + k1 <= i)
                    break label0;
            }
            j1 = i - 1;
        }
        j1 -= i1;
        sparsebooleanarray = mActionButtonGroups;
        sparsebooleanarray.clear();
        i3 = 0;
        i1 = 0;
        if(mStrictWidthLimit)
        {
            i1 = j3 / mMinCellSize;
            int j = mMinCellSize;
            i3 = mMinCellSize + (j3 % j) / i1;
        }
        k = 0;
        l1 = j3;
        j3 = k;
        k = ((flag1) ? 1 : 0);
_L2:
        int i2;
        int k2;
        int l2;
        int k3;
        MenuItemImpl menuitemimpl1;
        if(j3 >= l3)
            break MISSING_BLOCK_LABEL_768;
        menuitemimpl1 = (MenuItemImpl)arraylist.get(j3);
        if(!menuitemimpl1.requiresActionButton())
            break; /* Loop/switch isn't completed */
        View view = getItemView(menuitemimpl1, mScrapActionButtonView, viewgroup);
        if(mScrapActionButtonView == null)
            mScrapActionButtonView = view;
        if(mStrictWidthLimit)
            i1 -= ActionMenuView.measureChildForCells(view, i3, i1, i4, 0);
        else
            view.measure(i4, i4);
        l2 = view.getMeasuredWidth();
        i2 = l1 - l2;
        k2 = k;
        if(k == 0)
            k2 = l2;
        k = menuitemimpl1.getGroupId();
        if(k != 0)
            sparsebooleanarray.put(k, true);
        menuitemimpl1.setIsActionButton(true);
        k3 = j1;
        l2 = i1;
_L4:
        j3++;
        i1 = l2;
        k = k2;
        j1 = k3;
        l1 = i2;
        if(true) goto _L2; else goto _L1
_L1:
        l2 = i1;
        k2 = k;
        k3 = j1;
        i2 = l1;
        if(!menuitemimpl1.requestsActionButton()) goto _L4; else goto _L3
_L3:
        boolean flag4;
        k3 = menuitemimpl1.getGroupId();
        flag4 = sparsebooleanarray.get(k3);
        boolean flag2;
        boolean flag3;
        if((j1 > 0 || flag4) && l1 > 0 && (!mStrictWidthLimit || i1 > 0))
            flag2 = true;
        else
            flag2 = false;
        l2 = i1;
        k2 = k;
        flag3 = flag2;
        i2 = l1;
        if(flag2)
        {
            View view1 = getItemView(menuitemimpl1, mScrapActionButtonView, viewgroup);
            if(mScrapActionButtonView == null)
                mScrapActionButtonView = view1;
            if(mStrictWidthLimit)
            {
                k2 = ActionMenuView.measureChildForCells(view1, i3, i1, i4, 0);
                i2 = i1 - k2;
                i1 = i2;
                if(k2 == 0)
                {
                    flag2 = false;
                    i1 = i2;
                }
            } else
            {
                view1.measure(i4, i4);
            }
            l2 = view1.getMeasuredWidth();
            i2 = l1 - l2;
            k2 = k;
            if(k == 0)
                k2 = l2;
            if(mStrictWidthLimit)
            {
                if(i2 >= 0)
                    k = 1;
                else
                    k = 0;
                flag3 = flag2 & k;
                l2 = i1;
            } else
            {
                if(i2 + k2 > 0)
                    k = 1;
                else
                    k = 0;
                flag3 = flag2 & k;
                l2 = i1;
            }
        }
        if(!flag3 || k3 == 0) goto _L6; else goto _L5
_L5:
        sparsebooleanarray.put(k3, true);
        k = j1;
_L8:
        i1 = k;
        if(flag3)
            i1 = k - 1;
        menuitemimpl1.setIsActionButton(flag3);
        k3 = i1;
          goto _L4
_L6:
        k = j1;
        if(!flag4) goto _L8; else goto _L7
_L7:
        sparsebooleanarray.put(k3, false);
        i1 = 0;
_L10:
        k = j1;
        if(i1 >= j3) goto _L8; else goto _L9
_L9:
        MenuItemImpl menuitemimpl2 = (MenuItemImpl)arraylist.get(i1);
        int l = j1;
        if(menuitemimpl2.getGroupId() == k3)
        {
            l = j1;
            if(menuitemimpl2.isActionButton())
                l = j1 + 1;
            menuitemimpl2.setIsActionButton(false);
        }
        i1++;
        j1 = l;
          goto _L10
          goto _L8
        return true;
          goto _L4
    }

    public View getItemView(MenuItemImpl menuitemimpl, View view, ViewGroup viewgroup)
    {
        View view1 = menuitemimpl.getActionView();
        if(view1 == null || menuitemimpl.hasCollapsibleActionView())
        {
            view1 = view;
            if(!(view instanceof ActionMenuItemView))
                view1 = null;
            view1 = super.getItemView(menuitemimpl, view1, viewgroup);
        }
        byte byte0;
        if(menuitemimpl.isActionViewExpanded())
            byte0 = 8;
        else
            byte0 = 0;
        view1.setVisibility(byte0);
        menuitemimpl = (ActionMenuView)viewgroup;
        view = view1.getLayoutParams();
        if(!menuitemimpl.checkLayoutParams(view))
            view1.setLayoutParams(menuitemimpl.generateLayoutParams(view));
        return view1;
    }

    public MenuView getMenuView(ViewGroup viewgroup)
    {
        viewgroup = super.getMenuView(viewgroup);
        ((ActionMenuView)viewgroup).setPresenter(this);
        return viewgroup;
    }

    public boolean hideOverflowMenu()
    {
        if(mPostedOpenRunnable != null && mMenuView != null)
        {
            ((View)mMenuView).removeCallbacks(mPostedOpenRunnable);
            mPostedOpenRunnable = null;
            return true;
        }
        OverflowPopup overflowpopup = mOverflowPopup;
        if(overflowpopup != null)
        {
            overflowpopup.dismiss();
            return true;
        } else
        {
            return false;
        }
    }

    public boolean hideSubMenus()
    {
        if(mActionButtonPopup != null)
        {
            mActionButtonPopup.dismiss();
            return true;
        } else
        {
            return false;
        }
    }

    public void initForMenu(Context context, MenuBuilder menubuilder)
    {
        super.initForMenu(context, menubuilder);
        menubuilder = context.getResources();
        if(!mReserveOverflowSet)
            mReserveOverflow = reserveOverflow(mContext);
        if(!mWidthLimitSet)
            mWidthLimit = menubuilder.getDisplayMetrics().widthPixels / 2;
        if(!mMaxItemsSet)
            mMaxItems = ResourcesCompat.getResources_getInteger(context, com.actionbarsherlock.R.integer.abs__max_action_buttons);
        int i = mWidthLimit;
        if(mReserveOverflow)
        {
            if(mOverflowButton == null)
            {
                mOverflowButton = new OverflowMenuButton(mSystemContext);
                int j = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
                mOverflowButton.measure(j, j);
            }
            i -= mOverflowButton.getMeasuredWidth();
        } else
        {
            mOverflowButton = null;
        }
        mActionItemWidthLimit = i;
        mMinCellSize = (int)(56F * menubuilder.getDisplayMetrics().density);
        mScrapActionButtonView = null;
    }

    public boolean isOverflowMenuShowing()
    {
        return mOverflowPopup != null && mOverflowPopup.isShowing();
    }

    public boolean isOverflowReserved()
    {
        return mReserveOverflow;
    }

    public void onCloseMenu(MenuBuilder menubuilder, boolean flag)
    {
        dismissPopupMenus();
        super.onCloseMenu(menubuilder, flag);
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        if(!mMaxItemsSet)
        {
            mMaxItems = ResourcesCompat.getResources_getInteger(mContext, com.actionbarsherlock.R.integer.abs__max_action_buttons);
            if(mMenu != null)
                mMenu.onItemsChanged(true);
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable)
    {
        parcelable = (SavedState)parcelable;
        if(((SavedState) (parcelable)).openSubMenuId > 0)
        {
            parcelable = mMenu.findItem(((SavedState) (parcelable)).openSubMenuId);
            if(parcelable != null)
                onSubMenuSelected((SubMenuBuilder)parcelable.getSubMenu());
        }
    }

    public Parcelable onSaveInstanceState()
    {
        SavedState savedstate = new SavedState();
        savedstate.openSubMenuId = mOpenSubMenuId;
        return savedstate;
    }

    public boolean onSubMenuSelected(SubMenuBuilder submenubuilder)
    {
        if(submenubuilder.hasVisibleItems()) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        Object obj;
        for(obj = submenubuilder; ((SubMenuBuilder) (obj)).getParentMenu() != mMenu; obj = (SubMenuBuilder)((SubMenuBuilder) (obj)).getParentMenu());
        View view = findViewForItem(((SubMenuBuilder) (obj)).getItem());
        obj = view;
        if(view != null)
            break; /* Loop/switch isn't completed */
        if(mOverflowButton == null)
            continue; /* Loop/switch isn't completed */
        obj = mOverflowButton;
        break; /* Loop/switch isn't completed */
        if(true) goto _L1; else goto _L3
_L3:
        mOpenSubMenuId = submenubuilder.getItem().getItemId();
        mActionButtonPopup = new ActionButtonSubmenu(mContext, submenubuilder);
        mActionButtonPopup.setAnchorView(((View) (obj)));
        mActionButtonPopup.show();
        super.onSubMenuSelected(submenubuilder);
        return true;
    }

    public void onSubUiVisibilityChanged(boolean flag)
    {
        if(flag)
        {
            super.onSubMenuSelected(null);
            return;
        } else
        {
            mMenu.close(false);
            return;
        }
    }

    public void setExpandedActionViewsExclusive(boolean flag)
    {
        mExpandedActionViewsExclusive = flag;
    }

    public void setItemLimit(int i)
    {
        mMaxItems = i;
        mMaxItemsSet = true;
    }

    public void setReserveOverflow(boolean flag)
    {
        mReserveOverflow = flag;
        mReserveOverflowSet = true;
    }

    public void setWidthLimit(int i, boolean flag)
    {
        mWidthLimit = i;
        mStrictWidthLimit = flag;
        mWidthLimitSet = true;
    }

    public boolean shouldIncludeItem(int i, MenuItemImpl menuitemimpl)
    {
        return menuitemimpl.isActionButton();
    }

    public boolean showOverflowMenu()
    {
        if(mReserveOverflow && !isOverflowMenuShowing() && mMenu != null && mMenuView != null && mPostedOpenRunnable == null && !mMenu.getNonActionItems().isEmpty())
        {
            mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(mContext, mMenu, mOverflowButton, true));
            ((View)mMenuView).post(mPostedOpenRunnable);
            super.onSubMenuSelected(null);
            return true;
        } else
        {
            return false;
        }
    }

    public void updateMenuView(boolean flag)
    {
        super.updateMenuView(flag);
        if(mMenu != null)
        {
            ArrayList arraylist = mMenu.getActionItems();
            int k = arraylist.size();
            for(int i = 0; i < k; i++)
            {
                ActionProvider actionprovider = ((MenuItemImpl)arraylist.get(i)).getActionProvider();
                if(actionprovider != null)
                    actionprovider.setSubUiVisibilityListener(this);
            }

        }
        int j;
        boolean flag1;
        Object obj;
        if(mMenu != null)
            obj = mMenu.getNonActionItems();
        else
            obj = null;
        flag1 = false;
        j = ((flag1) ? 1 : 0);
        if(mReserveOverflow)
        {
            j = ((flag1) ? 1 : 0);
            if(obj != null)
            {
                j = ((ArrayList) (obj)).size();
                if(j == 1)
                {
                    if(!((MenuItemImpl)((ArrayList) (obj)).get(0)).isActionViewExpanded())
                        j = 1;
                    else
                        j = 0;
                } else
                if(j > 0)
                    j = 1;
                else
                    j = 0;
            }
        }
        if(j == 0) goto _L2; else goto _L1
_L1:
        if(mOverflowButton == null)
            mOverflowButton = new OverflowMenuButton(mSystemContext);
        obj = (ViewGroup)mOverflowButton.getParent();
        if(obj != mMenuView)
        {
            if(obj != null)
                ((ViewGroup) (obj)).removeView(mOverflowButton);
            obj = (ActionMenuView)mMenuView;
            ((ActionMenuView) (obj)).addView(mOverflowButton, ((ActionMenuView) (obj)).generateOverflowButtonLayoutParams());
        }
_L4:
        ((ActionMenuView)mMenuView).setOverflowReserved(mReserveOverflow);
        return;
_L2:
        if(mOverflowButton != null && mOverflowButton.getParent() == mMenuView)
            ((ViewGroup)mMenuView).removeView(mOverflowButton);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
    private ActionButtonSubmenu mActionButtonPopup;
    private int mActionItemWidthLimit;
    private boolean mExpandedActionViewsExclusive;
    private int mMaxItems;
    private boolean mMaxItemsSet;
    private int mMinCellSize;
    int mOpenSubMenuId;
    private View mOverflowButton;
    private OverflowPopup mOverflowPopup;
    final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
    private OpenOverflowRunnable mPostedOpenRunnable;
    private boolean mReserveOverflow;
    private boolean mReserveOverflowSet;
    private View mScrapActionButtonView;
    private boolean mStrictWidthLimit;
    private int mWidthLimit;
    private boolean mWidthLimitSet;



/*
    static OverflowPopup access$102(ActionMenuPresenter actionmenupresenter, OverflowPopup overflowpopup)
    {
        actionmenupresenter.mOverflowPopup = overflowpopup;
        return overflowpopup;
    }

*/



/*
    static ActionButtonSubmenu access$302(ActionMenuPresenter actionmenupresenter, ActionButtonSubmenu actionbuttonsubmenu)
    {
        actionmenupresenter.mActionButtonPopup = actionbuttonsubmenu;
        return actionbuttonsubmenu;
    }

*/


/*
    static OpenOverflowRunnable access$402(ActionMenuPresenter actionmenupresenter, OpenOverflowRunnable openoverflowrunnable)
    {
        actionmenupresenter.mPostedOpenRunnable = openoverflowrunnable;
        return openoverflowrunnable;
    }

*/
}
