from http import server
from http.server import HTTPServer, BaseHTTPRequestHandler

class helloHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header('content-type', 'text/html')
        

def main():
    PORT = 8000
    server = HTTPServer(('', PORT), helloHandler)
    print('Server running on port %s' % PORT)
    server.serve_forever()