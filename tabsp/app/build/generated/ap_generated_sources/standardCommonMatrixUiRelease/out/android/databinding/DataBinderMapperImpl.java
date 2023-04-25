package android.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.sunmnet.mediaroom.tabsp.DataBinderMapperImpl());
  }
}
