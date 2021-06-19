import * as React from 'react';

import { StyleSheet, View, Text, PermissionsAndroid } from 'react-native';
import {
  multiply,
  getAllLogs,
  getNotConnectedLogs,
  getByNumber,
  getMissedLogs,
} from 'rn-call-logs';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  const checker = async () => {
    try {
      const granted = await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.READ_CALL_LOG,
        {
          title: 'Call Log Example',
          message: 'Access your call logs',
          buttonNeutral: 'Ask Me Later',
          buttonNegative: 'Cancel',
          buttonPositive: 'OK',
        }
      );
      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
        // const x = await getNotConnectedLogs({
        //   fromEpoch: 0,
        //   toEpoch: 0,
        //   limit: 5,
        // });

        // const x = await getByNumber({
        //   fromEpoch: 0,
        //   toEpoch: 0,
        //   phoneNumber: '9995492179',
        //   limit: 20,
        //   type: 'OUTGOING',
        // });

        const x = await getMissedLogs({
          fromEpoch: 0,
          toEpoch: 0,
          limit: 10,
        });
        console.log('x=', x);
      } else {
        console.log('Call Log permission denied');
      }
    } catch (e) {
      console.log(e);
    }
  };

  React.useEffect(() => {
    multiply(3, 7).then(setResult);
    checker();
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
