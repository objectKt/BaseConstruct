package com.android.launcher.mileage;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.launcher.R;
import com.android.launcher.type.UnitType;
import com.android.launcher.util.NumberUtils;
import com.android.launcher.util.UnitUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @description:
 * @createDate: 2023/9/15
 */
public class MileageAdapter extends BaseMultiItemQuickAdapter<MileageMulItemEntity, BaseViewHolder> {

    public MileageAdapter(List<MileageMulItemEntity> data) {
        super(data);
        addItemType(MileageMulItemEntity.Type.SPEED.ordinal(), R.layout.fragment_mileage_speed);
        addItemType(MileageMulItemEntity.Type.DRIVING_DISTANCE.ordinal(), R.layout.mulitem_mileage_driving_distance);
        addItemType(MileageMulItemEntity.Type.LAUNCHER_AFTER.ordinal(), R.layout.mulitem_launcher_after);
        addItemType(MileageMulItemEntity.Type.RESTORE_AFTER.ordinal(), R.layout.mulitem_restore_after);

        addItemType(MileageMulItemEntity.Type.TREED_DRIVING_DISTANCE.ordinal(), R.layout.mulitem_mileage_treed_driving_distance);
        addItemType(MileageMulItemEntity.Type.TREED_LAUNCHER_AFTER.ordinal(), R.layout.mulitem_treed_launcher_after);
        addItemType(MileageMulItemEntity.Type.TREED_RESTORE_AFTER.ordinal(), R.layout.mulitem_treed_restore_after);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MileageMulItemEntity item) {
        if (item.getItemType() == MileageMulItemEntity.Type.SPEED.ordinal()) {
            setSpeedData(helper, item);
        } else if (item.getItemType() == MileageMulItemEntity.Type.DRIVING_DISTANCE.ordinal() || item.getItemType() == MileageMulItemEntity.Type.TREED_DRIVING_DISTANCE.ordinal()) {
            setDrivingDistanceData(helper, item);
        } else if (item.getItemType() == MileageMulItemEntity.Type.LAUNCHER_AFTER.ordinal() || item.getItemType() == MileageMulItemEntity.Type.TREED_LAUNCHER_AFTER.ordinal()) {
            setLauncherAfterData(helper, item);
        } else if (item.getItemType() == MileageMulItemEntity.Type.RESTORE_AFTER.ordinal() || item.getItemType() == MileageMulItemEntity.Type.TREED_RESTORE_AFTER.ordinal()) {
            setRestoreAfterData(helper, item);
        }
    }

    private void setRestoreAfterData(BaseViewHolder helper, MileageMulItemEntity item) {
        try {
            LaunchItemEntity launchItemEntity = (LaunchItemEntity) item.getData();

            helper.setText(R.id.titleTV, mContext.getResources().getString(R.string.after_resetting));

            TextView userMileTV = helper.getView(R.id.userMileTV);
            TextView userTimeTV = helper.getView(R.id.userTimeTV);
            TextView totalMileTV = helper.getView(R.id.totalMileTV);
            TextView totalTimeTV = helper.getView(R.id.totalTimeTV);
            TextView qtripTV = helper.getView(R.id.qtripTV);
            TextView averageSpeedTV = helper.getView(R.id.averageSpeedTV);

            if (launchItemEntity.getUnitType() == UnitType.KM.ordinal()) {
                userMileTV.setText(String.format("%skm", NumberUtils.floatRoundDTo(launchItemEntity.getUserMileage(), 2)));
                userTimeTV.setText(String.format("%sh", launchItemEntity.getUserTime()));
                totalMileTV.setText(String.format("%skm", NumberUtils.floatRoundDTo(launchItemEntity.getTotalMileage(), 2)));
                totalTimeTV.setText(String.format("%sh", launchItemEntity.getTotalTime()));
                qtripTV.setText(String.format("%sl/100km", launchItemEntity.getQtrip()));
                averageSpeedTV.setText(String.format("%skm/h", launchItemEntity.getSpeed()));
            } else {
                userMileTV.setText(String.format("%smi", NumberUtils.floatRoundDTo(UnitUtils.kmToMi(launchItemEntity.getUserMileage()), 2)));
                userTimeTV.setText(String.format("%sh", launchItemEntity.getUserTime()));
                totalMileTV.setText(String.format("%smi", NumberUtils.floatRoundDTo(UnitUtils.kmToMi(launchItemEntity.getTotalMileage()), 2)));
                totalTimeTV.setText(String.format("%sh", launchItemEntity.getTotalTime()));
                qtripTV.setText(String.format("%sl/100mi", NumberUtils.floatRoundDTo(UnitUtils.kmQtripToMiQtrip(launchItemEntity.getQtrip()), 1)));
                averageSpeedTV.setText(String.format("%smph", (int) UnitUtils.kmSpeedToMiSpeed(launchItemEntity.getSpeed())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLauncherAfterData(BaseViewHolder helper, MileageMulItemEntity item) {
        try {
            LaunchItemEntity launchItemEntity = (LaunchItemEntity) item.getData();

            helper.setText(R.id.titleTV, mContext.getResources().getString(R.string.after_starting_up));

            TextView userMileTV = helper.getView(R.id.userMileTV);
            TextView userTimeTV = helper.getView(R.id.userTimeTV);
            TextView totalMileTV = helper.getView(R.id.totalMileTV);
            TextView totalTimeTV = helper.getView(R.id.totalTimeTV);
            TextView qtripTV = helper.getView(R.id.qtripTV);
            TextView averageSpeedTV = helper.getView(R.id.averageSpeedTV);

            if (launchItemEntity.getUnitType() == UnitType.KM.ordinal()) {
                userMileTV.setText(String.format("%skm", NumberUtils.floatRoundDTo(launchItemEntity.getUserMileage(), 2)));
                userTimeTV.setText(String.format("%sh", launchItemEntity.getUserTime()));
                totalMileTV.setText(String.format("%skm", NumberUtils.floatRoundDTo(launchItemEntity.getTotalMileage(), 2)));
                totalTimeTV.setText(String.format("%sh", launchItemEntity.getTotalTime()));
                qtripTV.setText(String.format("%sl/100km", launchItemEntity.getQtrip()));
                averageSpeedTV.setText(String.format("%skm/h", launchItemEntity.getSpeed()));
            } else {
                userMileTV.setText(String.format("%smi", NumberUtils.floatRoundDTo(UnitUtils.kmToMi(launchItemEntity.getUserMileage()), 2)));
                userTimeTV.setText(String.format("%sh", launchItemEntity.getUserTime()));
                totalMileTV.setText(String.format("%smi", NumberUtils.floatRoundDTo(UnitUtils.kmToMi(launchItemEntity.getTotalMileage()), 2)));
                totalTimeTV.setText(String.format("%sh", launchItemEntity.getTotalTime()));
                qtripTV.setText(String.format("%sl/100mi", NumberUtils.floatRoundDTo(UnitUtils.kmQtripToMiQtrip(launchItemEntity.getQtrip()), 1)));
                averageSpeedTV.setText(String.format("%smph", (int) UnitUtils.kmSpeedToMiSpeed(launchItemEntity.getSpeed())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDrivingDistanceData(BaseViewHolder helper, MileageMulItemEntity item) {
        try {
            DrivingDistanceItemEntity data = (DrivingDistanceItemEntity) item.getData();
            helper.setText(R.id.drivingDistanceTitleTV, mContext.getResources().getString(R.string.potential_driving_distance))
                    .setText(R.id.energyConsumptionTV, mContext.getResources().getString(R.string.energy_consumption))
                    .setText(R.id.afterAveragingTV, mContext.getResources().getString(R.string.after_averaging));

            TextView potentialDrivingDistanceTV = helper.getView(R.id.potentialDrivingDistanceTV);
            TextView drivingDistanceQtripTV = helper.getView(R.id.drivingDistanceQtripTV);
            if (data.getUnitType() == UnitType.KM.ordinal()) {
                potentialDrivingDistanceTV.setText(NumberUtils.floatRoundDTo(data.getDistance(), 2) + "km");
                drivingDistanceQtripTV.setText(data.getQtrip() + "l/100km");
            } else {
                potentialDrivingDistanceTV.setText(NumberUtils.floatRoundDTo(data.getDistance(), 2) + "mi");
                drivingDistanceQtripTV.setText(data.getQtrip() + "l/100mi");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpeedData(BaseViewHolder helper, MileageMulItemEntity item) {
        try {
            SpeedItemEntity data = (SpeedItemEntity) item.getData();
            helper.setText(R.id.speedTV, String.valueOf(data.getSpeed()))
                    .setText(R.id.unitTV, mContext.getResources().getString(R.string.speed_unit));
            TextView unitTV = helper.getView(R.id.unitTV);
            if (data.getUnitType() == UnitType.KM.ordinal()) {
                unitTV.setText("km/h");
            } else {
                unitTV.setText("mph");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
