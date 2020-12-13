export default class Constant {
    public static SERVER_ADDRESS: string = 'http://localhost:8081/';
    public static FIND_PROPERTY_LIST: string = Constant.SERVER_ADDRESS + 'findPropertyList';
    public static FIND_RECORD_LIST_BY_PROP_ID = Constant.SERVER_ADDRESS + 'findRecordListByPropId';
    public static ADD_PROPERTY = Constant.SERVER_ADDRESS + 'addProperty';
    public static UPDATE_PROPERTY = Constant.SERVER_ADDRESS + 'updateProperty';
    public static DELETE_PROPERTY = Constant.SERVER_ADDRESS + 'deleteProperty';
    public static SET_PREVIOUS_VERSION = Constant.SERVER_ADDRESS + 'setPreviousVersion';
}