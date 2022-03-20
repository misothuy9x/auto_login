import requests
import re 

r = requests.get("http://qldt.actvn.edu.vn/CMCSoft.IU.Web.Info/Login.aspx")

cookie = r.headers['Set-Cookie'].split(';')[0]

data = re.findall(r'<input.+?name="(.+?)".+?value="(.*?)"', r.content.decode("utf-8"))

# txtUserName txtPassword

headers = {'User-Agent': 'Mozilla/5.0', 'Cookie': cookie}
payload = {'txtUserName':'user name ne`','txtPassword':'md5 ne`'}
for d in data:
  payload[d[0]] = d[1]

r = requests.post('http://qldt.actvn.edu.vn/CMCSoft.IU.Web.Info/Login.aspx',headers=headers,data=payload)

print(r.content.decode("utf-8"))