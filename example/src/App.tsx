import * as React from 'react';

import {
  StyleSheet,
  View,
  Text,
  PermissionsAndroid,
  FlatList,
  TouchableWithoutFeedback,
} from 'react-native';
import { getAllLogs } from 'rn-call-logs';
/* 
* getOutgoingLogs,
  getIncomingLogs,
  getMissedLogs,
  getRejectedLogs,
  getBlockedLogs,
  getExternallyAnsweredLogs,
  getByNumber,
  getNotConnectedLogs,
*/

interface logResponse {
  number: string;
  date: string;
  duration: string;
  country: string;
  type: string;
}

export default function App() {
  const [logs, setLogs] = React.useState<[logResponse] | []>();

  const getLogs = async () => {
    try {
      const granted = await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.READ_CALL_LOG,
        {
          title: 'RN Call Logs',
          message: 'Access your call logs',
          buttonNeutral: 'Ask Me Later',
          buttonNegative: 'Cancel',
          buttonPositive: 'OK',
        }
      );
      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
        try {
          const response: [logResponse] | [] = await getAllLogs({
            fromEpoch: 0,
            toEpoch: 0,
            limit: 10,
          });
          setLogs(response);
        } catch (error) {
          console.log(error);
        }

        // const y = await getByNumber({
        //   fromEpoch: 0,
        //   toEpoch: 0,
        //   phoneNumber: '9995492179',
        //   limit: 20,
        //   type: 'OUTGOING',
        // });

        // const z = await getMissedLogs({
        //   fromEpoch: 0,
        //   toEpoch: 0,
        //   limit: 10,
        // });
        // console.log('x=', x);
        // console.log('y=', y);
        // console.log('z=', z);
      } else {
        console.log('Call Log permission denied');
      }
    } catch (e) {
      console.log(e);
    }
  };

  React.useEffect(() => {
    getLogs();
  }, []);

  // UI Snippet from : https://www.itechinsiders.com/how-to-design-card-in-react-native/
  const renderItem = ({ item }: any) => (
    <TouchableWithoutFeedback>
      <View style={styles.mainCardView}>
        <View style={styles.cardInnerViewOne}>
          <View>
            <Text style={styles.cardMainText}>
              {item.number} ({item.country})
            </Text>
            <Text style={styles.cardSubText}>{item.type}</Text>
            <Text style={styles.cardSubText}>
              Duration: {item.duration} sec
            </Text>
            <Text style={styles.cardSubText}>
              Date:{new Date(parseInt(item.date)).toString()}
            </Text>
          </View>
        </View>
      </View>
    </TouchableWithoutFeedback>
  );
  return (
    <View style={styles.container}>
      <Text>Result:</Text>
      <FlatList
        data={logs}
        renderItem={renderItem}
        keyExtractor={(item) => item.date}
      />
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
  cardInnerViewOne: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  cardMainText: {
    fontSize: 14,
    color: 'black',
    fontWeight: 'bold',
    textTransform: 'capitalize',
  },
  cardSubText: {
    color: 'gray',
    fontSize: 12,
  },
  mainCardView: {
    height: 90,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
    borderRadius: 15,
    shadowColor: 'gray',
    shadowOffset: { width: 0, height: 0 },
    shadowOpacity: 1,
    shadowRadius: 8,
    elevation: 8,
    flexDirection: 'row',
    paddingLeft: 16,
    paddingRight: 14,
    margin: 10,
    minWidth: '90%',
  },
});
