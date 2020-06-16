<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MapsActivity" >

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <androidx.appcompat.widget.Toolbar
            android:id="@+id/toolbar2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@color/colorPrimaryDark"
            android:minHeight="?attr/actionBarSize"
            android:theme="?attr/actionBarTheme"
            app:navigationIcon="@drawable/ic_back"
            app:title="Order detail"
            app:titleTextColor="#FFFFFF" />

        <androidx.fragment.app.FragmentContainerView
            android:id="@+id/map"
            android:name="com.google.android.gms.maps.SupportMapFragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            tools:layout="@layout/activity_maps">

        </androidx.fragment.app.FragmentContainerView>
    </LinearLayout>

    <LinearLayout
        android:id="@+id/order_detail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom"
        android:background="@drawable/apstyle"
        android:gravity="center"
        android:orientation="vertical">

        <Button
            android:id="@+id/button_expand"
            style="@style/Widget.AppCompat.Button.Borderless"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="24dp"
            android:drawableStart="@drawable/ic_import_export_black_24dp"
            android:text="Order detail"
            android:textAllCaps="false"
            android:textColor="@color/colorAccent"
            android:textSize="20sp"
            android:textStyle="bold" />

        <LinearLayout
            android:id="@+id/linear"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">


            <LinearLayout
                android:layout_width="100dp"
                android:layout_height="match_parent"
                android:gravity="center"
                android:orientation="vertical"
                android:padding="10dp">

                <de.hdodenhof.circleimageview.CircleImageView
                    android:id="@+id/image_truck_detail"
                    android:layout_width="75dp"
                    android:layout_height="75dp"
                    android:padding="5dp"
                    android:src="@drawable/truck_1"
                    app:civ_border_color="@color/cardview_shadow_start_color" />

                <TextView
                    android:id="@+id/phone_detail"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:drawableStart="@drawable/ic_local_phone_black_24dp"
                    android:gravity="center"
                    android:text="01025942627"
                    android:textColor="#000000"
                    android:textSize="15dp"
                    android:textStyle="bold" />

                <TextView
                    android:id="@+id/price_detail"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="price:15$"
                    android:textColor="#00FF00"
                    android:textSize="15sp"
                    android:textStyle="bold" />

                <Button
                    android:id="@+id/button_startTrip"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:background="@drawable/button_default"
                    android:text="Start trip"
                    android:textAllCaps="false"
                    android:textColor="#FFFFFF" />

            </LinearLayout>

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical"
                android:padding="15dp">

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:gravity="end"
                    android:orientation="vertical"
                    android:paddingBottom="5dp">

                    <TextView
                        android:id="@+id/date_pos