package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

import java.util.List;

public class FaceAttributesResult {

    private double smile;
    private HeadPoseResult headPose;
    private String gender;
    private double age;
    private FacialHairResult facialHair;
    private String glasses;
    private EmotionResult emotion;
    private BlurResult blur;
    private ExposureResult exposure;
    private NoiseResult noise;
    private MakeupResult makeup;
    private List<AccessoryResult> accessories;
    private OcclusionResult occlusion;
    private HairResult hair;

    public FaceAttributesResult() {
    }

    public double getSmile() {
        return smile;
    }

    public void setSmile(double smile) {
        this.smile = smile;
    }

    public HeadPoseResult getHeadPose() {
        return headPose;
    }

    public void setHeadPose(HeadPoseResult headPose) {
        this.headPose = headPose;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public FacialHairResult getFacialHair() {
        return facialHair;
    }

    public void setFacialHair(FacialHairResult facialHair) {
        this.facialHair = facialHair;
    }

    public String getGlasses() {
        return glasses;
    }

    public void setGlasses(String glasses) {
        this.glasses = glasses;
    }

    public EmotionResult getEmotion() {
        return emotion;
    }

    public void setEmotion(EmotionResult emotion) {
        this.emotion = emotion;
    }

    public BlurResult getBlur() {
        return blur;
    }

    public void setBlur(BlurResult blur) {
        this.blur = blur;
    }

    public ExposureResult getExposure() {
        return exposure;
    }

    public void setExposure(ExposureResult exposure) {
        this.exposure = exposure;
    }

    public NoiseResult getNoise() {
        return noise;
    }

    public void setNoise(NoiseResult noise) {
        this.noise = noise;
    }

    public MakeupResult getMakeup() {
        return makeup;
    }

    public void setMakeup(MakeupResult makeup) {
        this.makeup = makeup;
    }

    public List<AccessoryResult> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<AccessoryResult> accessories) {
        this.accessories = accessories;
    }

    public OcclusionResult getOcclusion() {
        return occlusion;
    }

    public void setOcclusion(OcclusionResult occlusion) {
        this.occlusion = occlusion;
    }

    public HairResult getHair() {
        return hair;
    }

    public void setHair(HairResult hair) {
        this.hair = hair;
    }

    @NonNull
    @Override
    public String toString() {
        return "FaceAttributesResult{" + "smile=" + smile + ", headPose=" + headPose
                + ", gender='" + gender + '\'' + ", age=" + age + ", facialHair=" + facialHair
                + ", glasses='" + glasses + '\'' + ", emotion=" + emotion + ", blur=" + blur
                + ", exposure=" + exposure + ", noise=" + noise + ", makeup=" + makeup
                + ", accessories=" + accessories + ", occlusion=" + occlusion + ", hair=" + hair + '}';
    }
}
