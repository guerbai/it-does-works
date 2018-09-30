import time
import threading

class SumTask(object):

	def __init__(self):
		self._sub_sum = 0

	def run(self, i, num):
		start = i * num
		end = start + num
		sub_sum = 0
		while start < end:
			sub_sum += start
			start += 1
		self._sub_sum = sub_sum
	
	def get_result(self):
		return self._sub_sum


def measure_time_cost(thread_nums):
	nums = 99999999
	num_per_thread = int((nums + 1) / thread_nums)
	thread_list = [None] * thread_nums
	task_list = [None] * thread_nums
	start_at = time.time()
	for i in range(thread_nums):
		ct = SumTask()
		thread_list[i] = threading.Thread(target=ct.run, args=(i, num_per_thread))
		thread_list[i].start()
		task_list[i] = ct
	for i in range(thread_nums):
		thread_list[i].join()
	end_at = time.time()
	result = 0
	for i in range(thread_nums):
		result += task_list[i].get_result()
	print (result)
	return end_at - start_at


def main():
	thread_num1 = 1
	thread_num2 = 2
	thread_num4 = 4
	thread_num8 = 8
	print ("sum_task with thread_num1 cost time: " + str(measure_time_cost(thread_num1)) + 's in Python version.')
	print ("sum_task with thread_num2 cost time: " + str(measure_time_cost(thread_num2)) + 's in Python version.')
	print ("sum_task with thread_num4 cost time: " + str(measure_time_cost(thread_num4)) + 's in Python version.')
	print ("sum_task with thread_num4 cost time: " + str(measure_time_cost(thread_num4)) + 's in Python version.')
	

if __name__ == '__main__':
	main()