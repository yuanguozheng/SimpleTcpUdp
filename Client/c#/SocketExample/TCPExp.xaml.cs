using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using System.Net;
using System.Net.Sockets;
using System.IO;
using System.Threading;

namespace SocketExample
{
    /// <summary>
    /// TCPExp.xaml 的交互逻辑
    /// </summary>
    public partial class TCPExp : Window
    {
        public delegate void MessageHandler(string msg);

        Thread Receiver;
        TcpClient client = new TcpClient();
        NetworkStream ns;
        static MessageHandler handler;
        public TCPExp()
        {
            InitializeComponent();
            this.Closing += TCPExp_Closing;
            Receiver = new Thread(DoReceive);
        }

        void TCPExp_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            if (ns != null)
            {
                ns.Close();
                ns.Dispose();
            }
            if (client.Connected)
                client.Close();
        }

        public event MessageHandler ReceviedFromServer
        {
            add
            {
                handler += new MessageHandler(value);
            }
            remove
            {
                handler -= new MessageHandler(value);
            }
        }

        private async void Connect_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                await client.ConnectAsync(IPAddress.Parse(ServerIP.Text), int.Parse(Port.Text));
            }
            catch
            {
                MessageBox.Show("连接失败");
                return;
            }
            ns = client.GetStream();
            Receiver.Start();
            this.ReceviedFromServer += TCPExp_ReceviedFromServer;
            MessageBox.Show("连接成功");
        }

        void TCPExp_ReceviedFromServer(string msg)
        {
            Dispatcher.BeginInvoke((Action)(() =>
                {
                    Received.Text += msg;
                }));
        }

        async void DoReceive()
        {
            byte[] data = new byte[256];
            while (true)
            {
                await ns.ReadAsync(data, 0, 256);
                string str = Encoding.UTF8.GetString(data);
                str = str.Replace("\0", "");
                handler(string.Format("{0}\n", str));
                //Received.Text += string.Format("{0}:\n{1}\n", "Server", str);
            }
        }

        private async void Send_Click(object sender, RoutedEventArgs e)
        {
            byte[] data;
            data = Encoding.UTF8.GetBytes(SendContent.Text);
            await ns.WriteAsync(data, 0, data.Length);
        }
    }
}
