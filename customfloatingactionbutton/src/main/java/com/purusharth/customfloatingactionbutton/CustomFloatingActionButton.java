package com.purusharth.customfloatingactionbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;


public class CustomFloatingActionButton extends RelativeLayout{

    /**
     * mChildCount : denotes the number of menu items which need to be inflated
     * mDim : denotes the dimension of the entire square view
     */
    int mChildCount;
    int mDim;


    /**
     * The following instance variables store values for positioning of the entire view
     */
    int layout_marginLeft, layout_marginTop, layout_marginRight, layout_marginBottom;
    Boolean layout_alignParentLeft, layout_alignParentTop, layout_alignParentRight,
            layout_alignParentBottom, layout_centreInParent;


    /**
     * mQuadrant : denotes the quadrant into which the child items will expand into
     */
    int mQuadrant;


    /**
     * mParentDim : denotes the dimension the parent FAB
     * mChildDim : denotes the dimension of the child FABs
     */
    int mParentDim, mChildDim;
    int mViewPadding;


    /**
     * mParentView : view for holding the parent FAB view, inflated from floating_card.xml
     * mChildView : view array for holding the child FAB views, inflated from floating_card.xml
     */
    View mParentView;
    View[] mChildViews;


    /**
     * mParentBackgroundTint : stores the default background colour resource for parent FAB
     * mChildBackgroundTint : stores the default background colour resource for child FABs
     */
    int mParentBackgroundTint;
    int mChildBackgroundTint;


    /**
     * mChildViewAngleInclinations : stores the angles along which the child FABs are supposed to fly off
     * mChildTranslation : denotes the distance between a child FAB and the parent FAB
     */
    double[] mChildViewAngleInclinations;
    int mChildTranslation;


    /**
     * mParentViewClickListener : the onClickListener for the parent FAB
     * mChildViewClickListeners : the array which stores the onClickListeners for child FABs
     */
    View.OnClickListener mParentViewClickListener;
    View.OnClickListener[] mChildViewClickListeners;


    /**
     * mParentClickListeners : ArrayList which intends to store the list of actions to be performed
     * on a click. The only clickListener for the parent view specified above will execute the onClick
     * method for all the onClickListeners added for the parent FAB. This allows the user to attach
     * multiple onClickListeners to the parent FAB, and perform multiple kinds of actions on a click.
     *
     * mChildClickListeners : An array of array lists, whose each array list item stores the actions
     * needed to be performed for an the corresponding child FAB, just like the mParentClickListeners
     * array list.
     */
    ArrayList<View.OnClickListener> mParentClickListeners;
    ArrayList[] mChildClickListeners;


    /**
     * mExpansionClickListener : clickListener which initiates expansion / fly off of the child FABs
     * mBounceClickListener : clickListener which initiates a bounce animation every time a FAB is
     * clicked upon
     */
    View.OnClickListener mExpansionClickListener;
    View.OnClickListener mBounceClickListener;


    /**
     * mHasExpanded : stores whether the child FABs have flown out or not
     * mAreChildViewsVisible : stores whether the child FABs are currently visible or not
     */
    Boolean mHasExpanded, mAreChildViewsVisible;


    public CustomFloatingActionButton(Context context) {
        super(context);
    }

    public CustomFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    /**
     *
     * @param context : context object
     * @param attrs : attribute set obtained from inflating the xml
     *              initializes the instance variables of the class
     */
    private void init(Context context, AttributeSet attrs){
        final TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomFloatingActionButton, 0, 0);

        mChildCount = typedArray.getInteger(R.styleable.CustomFloatingActionButton_childCount, mChildCount);

        mChildViews = new View[mChildCount];
        mChildViewAngleInclinations = new double[mChildCount];
        mChildViewClickListeners = new View.OnClickListener[mChildCount];
        mChildClickListeners= new ArrayList[mChildCount];
        mParentClickListeners = new ArrayList<>();
        for(int i = 0;i < mChildCount;i ++){
            mChildClickListeners[i] = new ArrayList();
        }

        setDefaults();

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_dim)){
            mDim = typedArray.getDimensionPixelSize(R.styleable.
                    CustomFloatingActionButton_dim, mDim);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_parentDim)){
            mParentDim = typedArray.getDimensionPixelSize(R.styleable.
                    CustomFloatingActionButton_parentDim, mParentDim);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_childDim)){
            mChildDim = typedArray.getDimensionPixelSize(R.styleable.
                    CustomFloatingActionButton_childDim, mChildDim);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_layout_marginLeft)){
            layout_marginLeft = typedArray.getDimensionPixelSize(R.styleable.
                    CustomFloatingActionButton_layout_marginLeft, layout_marginLeft);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_layout_marginTop)){
            layout_marginTop = typedArray.getDimensionPixelSize(R.styleable.
                    CustomFloatingActionButton_layout_marginTop, layout_marginTop);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_layout_marginRight)){
            layout_marginRight = typedArray.getDimensionPixelSize(R.styleable.
                    CustomFloatingActionButton_layout_marginRight, layout_marginRight);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_layout_marginBottom)){
            layout_marginBottom = typedArray.getDimensionPixelSize(R.styleable.
                    CustomFloatingActionButton_layout_marginBottom, layout_marginBottom);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_layout_alignParentLeft)){
            layout_alignParentLeft = typedArray.getBoolean(R.styleable.
                    CustomFloatingActionButton_layout_alignParentLeft, layout_alignParentLeft);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_layout_alignParentTop)){
            layout_alignParentTop = typedArray.getBoolean(R.styleable.
                    CustomFloatingActionButton_layout_alignParentTop, layout_alignParentTop);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_layout_alignParentRight)){
            layout_alignParentRight = typedArray.getBoolean(R.styleable.
                    CustomFloatingActionButton_layout_alignParentRight, layout_alignParentRight);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_layout_alignParentBottom)){
            layout_alignParentBottom = typedArray.getBoolean(R.styleable.
                    CustomFloatingActionButton_layout_alignParentBottom, layout_alignParentBottom);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_layout_centerInParent)){
            layout_centreInParent = typedArray.getBoolean(R.styleable.
                    CustomFloatingActionButton_layout_centerInParent, layout_centreInParent);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_parentBackgroundTint)){
            mParentBackgroundTint = typedArray.getColor(R.styleable.
                    CustomFloatingActionButton_parentBackgroundTint, mParentBackgroundTint);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_childBackgroundTint)){
            mChildBackgroundTint = typedArray.getColor(R.styleable.
                    CustomFloatingActionButton_childBackgroundTint, mChildBackgroundTint);
        }

        if(typedArray.hasValue(R.styleable.CustomFloatingActionButton_quadrant)){
            mQuadrant = typedArray.getInteger(R.styleable.
                    CustomFloatingActionButton_quadrant, mQuadrant);
            if(mQuadrant < 1 || mQuadrant > 4){
                mQuadrant = 1;
            }
        }

        typedArray.recycle();
        addViews(context);
        mHasExpanded = false;
        mAreChildViewsVisible = false;
    }


    /**
     * The onAttachedToWindow view lifecycle callback is overridden to setup our custom FAB
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        positionViews();
        hideChildViews();

        setUpBackgrounds();

        calculateAngles();

        setUpExpansionClickListener();
        setUpBounceClickListener();
        addListenersToArrayList();
        setUpViewClickListeners();
    }


    /**
     * initializes the instance variables with default values
     */
    private void setDefaults(){
        mDim = getResources().getDimensionPixelSize(R.dimen.customFab_default);
        mParentDim = getResources().getDimensionPixelSize(R.dimen.parent_default);
        mChildDim = getResources().getDimensionPixelSize(R.dimen.child_default);
        mViewPadding = getResources().getDimensionPixelSize(R.dimen.floating_card_padding);

        mQuadrant = 1;

        layout_marginLeft = layout_marginTop = layout_marginRight = layout_marginBottom = 0;
        layout_alignParentLeft = false;
        layout_alignParentTop = false;
        layout_alignParentRight = false;
        layout_alignParentBottom = false;
        layout_centreInParent = false;

        mParentBackgroundTint = getResources().getColor(R.color.parent_default);
        mChildBackgroundTint = getResources().getColor(R.color.child_default);

    }


    /**
     *
     * @param context : context object
     *                adds all the FABs to this view group
     */
    private void addViews(Context context){
        mParentView = (LayoutInflater.from(context).inflate(R.layout.floating_card,
                this, false));
        addView(mParentView);
        for(int i = 0;i < mChildCount;i ++){
            mChildViews[i] = (LayoutInflater.from(context).inflate(R.layout.floating_card,
                    this, false));
            addView(mChildViews[i]);
        }
    }


    /**
     * positions all the views within the view group, depending upon the value of mQuadrant
     */
    private void positionViews(){
        addAlignmentsToView(this, layout_alignParentLeft, layout_alignParentTop,
                layout_alignParentRight, layout_alignParentBottom, layout_centreInParent);
        addMarginsToView(this, layout_marginLeft, layout_marginTop, layout_marginRight,
                layout_marginBottom);

        if(mQuadrant == 1) {
            addMarginsToView(mParentView, 0, (mDim - mParentDim - 2 * mViewPadding),
                    0, 0);
        }
        if(mQuadrant == 2){
            addMarginsToView(mParentView, (mDim - mParentDim - 2 * mViewPadding),
                    (mDim - mParentDim - 2 * mViewPadding), 0, 0);
        }
        if(mQuadrant == 3){
            addMarginsToView(mParentView, (mDim - mParentDim - 2 * mViewPadding), 0,
                    0, 0);
        }

        for(int i = 0;i < mChildCount;i ++) {
            if(mQuadrant == 1) {
                addMarginsToView(mChildViews[i], 0, (mDim - mChildDim - 2 * mViewPadding),
                        0, 0);
            }
            if(mQuadrant == 2){
                addMarginsToView(mChildViews[i], (mDim - mChildDim - 2 * mViewPadding),
                        (mDim - mChildDim - 2 * mViewPadding), 0, 0);
            }
            if(mQuadrant == 3){
                addMarginsToView(mChildViews[i], (mDim - mChildDim - 2 * mViewPadding), 0,
                        0, 0);
            }
        }

        setDimensions();
        this.setElevation(1000);
    }


    /**
     * @param view : view which must be aligned
     * @param left : boolean value align_ParentLeft
     * @param top : boolean value align_ParentTop
     * @param right : boolean value align_ParentRight
     * @param bottom : boolean value align_ParentBottom
     * @param centre : boolean value centre_InParent
     */
    private void addAlignmentsToView(View view, Boolean left, Boolean top, Boolean right,
                                     Boolean bottom, Boolean centre){
        if(view.getLayoutParams() instanceof RelativeLayout.LayoutParams){
            LayoutParams alignmentParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            if (left)
                alignmentParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            if (top)
                alignmentParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            if (right)
                alignmentParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            if (bottom)
                alignmentParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            if (centre)
                alignmentParams.addRule(RelativeLayout.CENTER_IN_PARENT);

            view.setLayoutParams(alignmentParams);
        }
    }


    /**
     * @param view : view to which margins must be added
     * @param left : left margin
     * @param top : top margin
     * @param right : right margin
     * @param bottom : bottom margin
     */
    private void addMarginsToView(View view, int left, int top, int right, int bottom){
        if(getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)
                    (view.getLayoutParams());
            marginLayoutParams.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }


    /**
     * sets the dimensions of the views within the view group
     */
    @SuppressLint("CutPasteId")
    private void setDimensions(){
        getLayoutParams().width = mDim;
        getLayoutParams().height = mDim;

        mParentView.findViewById(R.id.rootRelativeLayout).getLayoutParams().width = mParentDim;
        mParentView.findViewById(R.id.rootRelativeLayout).getLayoutParams().height = mParentDim;
        ((CardView) mParentView.findViewById(R.id.rootCardView)).setRadius(mParentDim / 2);

        mParentView.findViewById(R.id.innerRelativeLayout).getLayoutParams().width =
                (int)(mParentDim * 0.75f);
        mParentView.findViewById(R.id.innerRelativeLayout).getLayoutParams().height =
                (int)(mParentDim * 0.75f);

        ((CardView) mParentView.findViewById(R.id.innerCardView)).setRadius(
                (int)(mParentDim * 0.75f / 2));

        for(int i = 0;i < mChildCount;i ++){
            mChildViews[i].findViewById(R.id.rootRelativeLayout).getLayoutParams().width = mChildDim;
            mChildViews[i].findViewById(R.id.rootRelativeLayout).getLayoutParams().height = mChildDim;
            ((CardView) mChildViews[i].findViewById(R.id.rootCardView)).setRadius(mChildDim / 2);

            mChildViews[i].findViewById(R.id.innerRelativeLayout).getLayoutParams().width =
                    (int)(mChildDim * 0.8f);
            mChildViews[i].findViewById(R.id.innerRelativeLayout).getLayoutParams().height =
                    (int)(mChildDim * 0.8f);

            ((CardView) mChildViews[i].findViewById(R.id.innerCardView)).setRadius(
                    (int)(mChildDim * 0.8f / 2));
        }

        mChildTranslation = mDim - mChildDim - 2 * mViewPadding;
    }


    /**
     * sets the default background tints for all FABs
     */
    private void setUpBackgrounds(){
        setParentBackgroundTint(mParentBackgroundTint);
        for(int i = 0;i < mChildCount;i ++){
            setChildBackgroundTint(mChildBackgroundTint, i);
        }
    }


    /**
     * calculates angles for each FAB along which they must fly off
     */
    private void calculateAngles(){
        double theta_inc = (Math.PI / 2) / (mChildCount - 1);
        double curr_theta = 0 + (mQuadrant - 1) * (Math.PI / 2);
        for(int i = 0;i < mChildCount;i ++){
            mChildViewAngleInclinations[i] = curr_theta;
            curr_theta += theta_inc;
        }
    }


    /**
     * hides and all child FABs
     */
    private void hideChildViews(){
        for(int i = 0;i < mChildCount;i ++){
            mChildViews[i].setScaleX(0.0f);
            mChildViews[i].setScaleY(0.0f);
            mChildViews[i].setVisibility(GONE);
        }
    }


    /**
     * shows all child FABs
     */
    private void showChildViews(){
        for(int i = 0;i < mChildCount;i ++){
            mChildViews[i].setVisibility(VISIBLE);
        }
    }


    /**
     * sets up the expansionClickListener, which needs to ensure the child FABs fly off at correct
     * angles and disappear and reappear on further clicks with proper animations
     */
    private void setUpExpansionClickListener(){
        mExpansionClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // for the case when the child FABs haven't flown off yet
                if(!mHasExpanded) {
                    showChildViews();
                    for (int i = 0; i < mChildCount; i++) {
                        mChildViews[i].clearAnimation();
                        mChildViews[i].animate().setDuration(350).translationX((float)
                                (mChildTranslation * Math.cos(mChildViewAngleInclinations[i])));
                        mChildViews[i].animate().translationY((float)
                                (-mChildTranslation * Math.sin(mChildViewAngleInclinations[i])));

                        mChildViews[i].animate().scaleX(1.0f);
                        mChildViews[i].animate().scaleY(1.0f);
                    }
                    mHasExpanded = true;
                    mAreChildViewsVisible = true;
                }
                // for the case when the child FABs have flown off
                else{
                    if(mAreChildViewsVisible){
                        for(int i = 0;i < mChildCount;i ++){
                            mChildViews[i].clearAnimation();
                            mChildViews[i].animate().scaleX(0.000001f);
                            final int finalI = i;
                            mChildViews[i].animate().scaleY(0.000001f).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    mChildViews[finalI].setVisibility(GONE);
                                }
                            });
                        }
                        mAreChildViewsVisible = false;
                    }
                    else{
                        for(int i = 0;i < mChildCount;i ++){
                            mChildViews[i].clearAnimation();
                            mChildViews[i].setVisibility(VISIBLE);
                            mChildViews[i].animate().scaleX(1.0f);
                            mChildViews[i].animate().scaleY(1.0f);
                        }
                        mAreChildViewsVisible = true;
                    }
                }
            }
        };
    }


    /**
     * sets up the bounceClickListener, which initiates a bounce animation every time the view it is
     * set upon is clicked upon
     */
    private void setUpBounceClickListener(){
        mBounceClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                view.clearAnimation();
                view.animate().scaleX(0.85f).setDuration(200).withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        view.animate().scaleY(0.85f).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                view.animate().scaleX(1.0f).withStartAction(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                view.animate().scaleY(1.0f);
                                            }
                                        }
                                );
                            }
                        });
                    }
                });
            }
        };
    }


    /**
     * adds all the clickListeners to their array lists
     */
    private void addListenersToArrayList() {
        addParentClickListener(mExpansionClickListener);
        addParentClickListener(mBounceClickListener);
        for(int i = 0;i < mChildCount;i ++){
            addChildClickListener(mBounceClickListener, i);
        }
    }


    /**
     * sets up the onclick method for the listeners which will be actually set upon FABs
     */
    private void setUpViewClickListeners(){
        mParentViewClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;i < mParentClickListeners.size();i ++){
                    mParentClickListeners.get(i).onClick(v);
                }
            }
        };

        for(int i = 0;i < mChildCount;i ++){
            final int index = i;
            mChildViewClickListeners[index] = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j = 0;j < mChildClickListeners[index].size();j ++){
                        ((View.OnClickListener) mChildClickListeners[index].get(j)).onClick(v);
                    }
                }
            };
        }

        mParentView.setOnClickListener(mParentViewClickListener);
        mParentView.findViewById(R.id.imageButtonView).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentViewClickListener.onClick(mParentView);
            }
        });
        for(int i = 0;i < mChildCount;i ++){
            mChildViews[i].setOnClickListener(mChildViewClickListeners[i]);
            final int finalI = i;
            mChildViews[i].findViewById(R.id.imageButtonView).setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mChildViewClickListeners[finalI].onClick(mChildViews[finalI]);
                        }
                    }
            );
        }

    }


    /**
     * @param onClickListener : click listener object
     *                        adds the clickListener object for the parent FAB.
     */
    public void addParentClickListener(View.OnClickListener onClickListener){
        mParentClickListeners.add(onClickListener);
    }


    /**
     * @param onClickListener : click listener object
     * @param child : child FAB index
     *              adds the clickListener object for a child FAB.
     */
    public void addChildClickListener(View.OnClickListener onClickListener, int child){
        mChildClickListeners[child].add(onClickListener);
    }


    /**
     * @param resource : image resource
     *                 adds image background for parent FAB
     */
    public void setParentImageResource(int resource){
        ((ImageView) mParentView.findViewById(R.id.imageButtonView)).setImageResource(resource);
    }


    /**
     * @param resource : image resource
     * @param child : child FAB index
     *              adds image background for a child FAB
     */
    public void setChildImageResource(int resource, int child){
        ((ImageView) mChildViews[child].findViewById(R.id.imageButtonView)).setImageResource(resource);
    }


    /**
     * @param color : color resource
     *              adds background tint on parent FAB
     */
    public void setParentBackgroundTint(int color){
        ((CardView) mParentView.findViewById(R.id.rootCardView))
                .setCardBackgroundColor(color);
        ((CardView) mParentView.findViewById(R.id.innerCardView))
                .setCardBackgroundColor(color);
        mParentView.findViewById(R.id.imageButtonView).setBackgroundColor(color);

    }


    /**
     * @param color : color resource
     * @param child : child FAB index
     *              adds background tint on a child FAB
     */
    public void setChildBackgroundTint(int color, int child){
        ((CardView) mChildViews[child].findViewById(R.id.rootCardView))
                .setCardBackgroundColor(color);
        ((CardView) mChildViews[child].findViewById(R.id.innerCardView))
                .setCardBackgroundColor(color);
        mChildViews[child].findViewById(R.id.imageButtonView).
                setBackgroundColor(color);
    }


    /** Getter Methods **/

    public int getChildrenCount() {
        return mChildCount;
    }

    public int getDim() {
        return mDim;
    }

    public int getLayout_marginLeft() {
        return layout_marginLeft;
    }

    public int getLayout_marginTop() {
        return layout_marginTop;
    }

    public int getLayout_marginRight() {
        return layout_marginRight;
    }

    public int getLayout_marginBottom() {
        return layout_marginBottom;
    }

    public Boolean getLayout_alignParentLeft() {
        return layout_alignParentLeft;
    }

    public Boolean getLayout_alignParentTop() {
        return layout_alignParentTop;
    }

    public Boolean getLayout_alignParentRight() {
        return layout_alignParentRight;
    }

    public Boolean getLayout_alignParentBottom() {
        return layout_alignParentBottom;
    }

    public Boolean getLayout_centreInParent() {
        return layout_centreInParent;
    }

    public int getQuadrant() {
        return mQuadrant;
    }

    public int getParentDim() {
        return mParentDim;
    }

    public int getChildDim() {
        return mChildDim;
    }

}