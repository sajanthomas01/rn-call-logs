import { NativeModules } from 'react-native';

interface LogArrayData {
  name?: string;
  number: string;
  date?: string;
  duration: string;
  country?: string;
  type: string;
}

type RnCallLogsType = {
  multiply(a: number, b: number): Promise<number>;
  getAllLogs(limit: number, skip: number): Promise<[LogArrayData] | string>;
};

const { RnCallLogs } = NativeModules;
const RNCallLog = RnCallLogs as RnCallLogsType;
const { getAllLogs, multiply } = RNCallLog;
export { getAllLogs, multiply };
