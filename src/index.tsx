import { NativeModules } from 'react-native';

type RnCallLogsType = {
  multiply(a: number, b: number): Promise<number>;
};

const { RnCallLogs } = NativeModules;

export default RnCallLogs as RnCallLogsType;
