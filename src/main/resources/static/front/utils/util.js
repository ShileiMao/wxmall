const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

const resolveAddressPromise = (addr, qqMap) => {
  let promise1 = new Promise((resolve, reject) => {
    qqMap.geocoder({
      address: addr,
      success: (res) => {
        resolve(res);
      },
      fail: (e) => {
        console.error("Failed resolve address: " + e);
        reject(e);
      }
    })
  });
  return promise1;
}
const getDistance = (addr1, addr2, qqMap, callback) => {
  console.log("start getting address: " + addr1 + ", addr2: " + addr2);
  let promise = new Promise((resolve, reject) => {

    resolveAddressPromise(addr1, qqMap).then(res => {
      console.log("resolve address: "+ JSON.stringify(res));
      if(res.status === 0) {
        
        let loc1 = res.result.location;

        console.log("resolve address: "+ JSON.stringify(loc1));
        resolveAddressPromise(addr2, qqMap). then (res2 => {
          if (res2.status === 0) {

            
            let loc2 = res2.result.location;
            console.log("resolve address2 : "+ JSON.stringify(loc2));

            qqMap.calculateDistance({
              mode: 'driving',
              from: "" + loc1.lat + "," + loc1.lng + "",
              to: "" + loc2.lat + "," + loc2.lng + "",
              success: res3 => {
                console.log("distance: " + JSON.stringify(res3));
                resolve(res3);
              },
              fail: error => {
                console.log("resolve distance error: " + JSON.stringify(error));
                reject(error);
              }
            })
          }
        });
      }
    })

  })
    
  return promise;
}

module.exports = {
  formatTime: formatTime,
  getDistance: getDistance
}
